import model.*;
import render.Render;
import transforms.*;
import view.Toolbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Created by Dominik Mandinec
 */

public class Canvas {
    private JFrame frame;
    private JPanel panel;
    private Toolbar toolbar;
    private BufferedImage img;
    private Render render;
    private Camera camera;
    private Mat4 view;
    private Mat4 proj;
    private Mat4 model;

    private int mousePositionX;
    private int mousePositionY;
    private int newMousePosX;
    private int newMousePosY;

    private int click;

    private Boolean cube = false;
    private Boolean tetra = false;
    private Boolean grid = false;
    private Boolean pyramid = false;
    private Boolean bicubicGrid = false;

    private Mat4 selected;

    private int bicubicGridcolor = 0xEE6628;



    public Canvas(int width, int height) {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height));

        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        toolbar = new Toolbar();
        toolbar.getRbBezier().setSelected(true);
        frame.add(toolbar.getToolBar(), BorderLayout.PAGE_START);

        render = new Render();
        render.setBufferedImage(img);

        model = new Mat4Identity();
        proj = new Mat4OrthoRH(20, 20, 0.1, 200);
        camera = new Camera(new Vec3D(-10, 0, 0), 0, 0, 1, true);
        view = camera.getViewMatrix();

        if (toolbar.getRbBezier().isSelected())
                selected = Cubic.BEZIER;
        if (toolbar.getRbCoons().isSelected())
                selected = Cubic.COONS;
        if (toolbar.getRbFerguson().isSelected())
                selected = Cubic.FERGUSON;



        draw();

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePositionX = e.getX();
                mousePositionY = e.getY();
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent g) {
                click = g.getButton();
                panel.addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        newMousePosX = mousePositionX;
                        newMousePosY = mousePositionY;

                        mousePositionX = e.getX();
                        mousePositionY = e.getY();
                        if (click == MouseEvent.BUTTON1) {
                            camera = camera.addAzimuth(-(mousePositionX - newMousePosX) * Math.PI / 360);
                            camera = camera.addZenith(-(mousePositionY - newMousePosY) * Math.PI / 360);
                        } else if (click == MouseEvent.BUTTON3) {
                            Mat4 rot = new Mat4RotXYZ(0, -(mousePositionY - newMousePosY) * 0.02, (mousePositionX - newMousePosX) * 0.02);
                            model = model.mul(rot);
                        }
                        draw();
                    }
                });
            }
        });

        panel.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.isControlDown()) {
                    if (e.getWheelRotation() < 0) {
                        camera = camera.forward(1.0);
                    } else {
                        camera = camera.backward(1.0);
                    }
                } else if (!e.isControlDown()) {
                    Mat4 scale;
                    if (e.getWheelRotation() < 0) {
                        scale = new Mat4Scale(1.1, 1.1, 1.1);
                    } else {
                        scale = new Mat4Scale(0.9, 0.9, 0.9);
                    }
                    model = model.mul(scale);
                }
                draw();
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    camera = camera.up(0.5);
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    camera = camera.down(0.5);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    camera = camera.right(0.5);
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    camera = camera.left(0.5);
                } else if (e.getKeyCode() == KeyEvent.VK_U) {
                    model = model.mul(new Mat4Transl(0, 0, 0.2));
                } else if (e.getKeyCode() == KeyEvent.VK_I) {
                    model = model.mul(new Mat4Transl(0.2, 0, 0));
                } else if (e.getKeyCode() == KeyEvent.VK_O) {
                    model = model.mul(new Mat4Transl(0, 0.2, 0));
                } else if (e.getKeyCode() == KeyEvent.VK_J) {
                    model = model.mul(new Mat4Transl(0, 0, -0.2));
                } else if (e.getKeyCode() == KeyEvent.VK_K) {
                    model = model.mul(new Mat4Transl(-0.2, 0, 0));
                } else if (e.getKeyCode() == KeyEvent.VK_L) {
                    model = model.mul(new Mat4Transl(0, -0.2, 0));
                }
                draw();
            }
        });
        actionButtons();
    }

    private void actionButtons() {
        toolbar.getBtnOr().addActionListener(e -> setCameraView(new Mat4OrthoRH(20, 20, 0.1, 200)));
        toolbar.getBtnPer().addActionListener(e -> setCameraView(new Mat4PerspRH(Math.PI / 4, 1, 0.01, 30)));
        toolbar.getBtnReset().addActionListener(e -> reset());
        toolbar.getBtnCube().addActionListener(e -> objectOnOff(0));
        toolbar.getBtnTetra().addActionListener(e -> objectOnOff(1));
        toolbar.getBtnGrid().addActionListener(e -> objectOnOff(2));
        toolbar.getBtnPyramid().addActionListener(e -> objectOnOff(3));
        toolbar.getBtnBicubicGrid().addActionListener(e -> objectOnOff(4));
        toolbar.getRbCoons().addItemListener(e -> bicubicGridDraw(e));
        toolbar.getRbBezier().addItemListener(e -> bicubicGridDraw(e));
        toolbar.getRbFerguson().addItemListener(e -> bicubicGridDraw(e));
    }

    private void bicubicGridDraw(ItemEvent e){
        if (e.getStateChange() == ItemEvent.SELECTED) {
            System.out.println(toolbar.getRbBezier().isSelected());
            System.out.println(toolbar.getRbCoons().isSelected());
            System.out.println(toolbar.getRbFerguson().isSelected());

            if (toolbar.getRbBezier().isSelected()){
                selected = Cubic.BEZIER;
                bicubicGridcolor = 0xEE6628;
            }

            if (toolbar.getRbCoons().isSelected()){
                selected = Cubic.COONS;
                bicubicGridcolor = 0xDF0076;

            }
            if (toolbar.getRbFerguson().isSelected()){
                selected = Cubic.FERGUSON;
                bicubicGridcolor = 0x7B04DD;

            }
            draw();
        }
    }

    private void objectOnOff(int obj) {
        switch (obj) {
            case 0:
                if (cube) {
                    cube = false;
                } else {
                    cube = true;
                }
                break;
            case 1:
                if (tetra) {
                    tetra = false;
                } else {
                    tetra = true;
                }
                break;
            case 2:
                if (grid) {
                    grid = false;
                } else {
                    grid = true;
                }
                break;
            case 3:
                if (pyramid) {
                    pyramid = false;
                } else {
                    pyramid = true;
                }
                break;
            case 4:
                if (bicubicGrid) {
                    bicubicGrid = false;
                } else {
                    bicubicGrid = true;
                }
                break;
        }
        draw();
    }

    private void setCameraView(Mat4 proj) {
        this.proj = proj;
        draw();
    }

    private void reset() {
        setCameraView(new Mat4OrthoRH(20, 20, 0.1, 200));
        camera = new Camera(new Vec3D(-10, 0, 0), 0, 0, 1, true);
        model = new Mat4Identity();
        view = camera.getViewMatrix();
        cube = false;
        tetra = false;
        grid = false;
        pyramid = false;
        bicubicGrid = false;
        bicubicGridcolor = 0xEE6628;
        toolbar.getRbBezier().setSelected(true);
        draw();
    }


    public void draw() {
        clear();
        render.setView(camera.getViewMatrix());
        render.setProj(proj);

        if (cube) {
            render.draw(new Cube(), this.model);
        }
        if (tetra) {
            render.draw(new Tetrahedron(), this.model);
        }
        if (grid) {
            render.draw(new Grid(5, 5), this.model);
        }
        if (pyramid) {
            render.draw(new Pyramid(), this.model);
        }
        if (bicubicGrid) {
            render.draw(new BicubicGrid(selected, bicubicGridcolor), this.model);
        }

        render.draw(new XyzRgb(), this.model);
        present();
    }

    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x6f6f6f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public void present() {
        if (panel.getGraphics() != null)
            panel.getGraphics().drawImage(img, 0, 0, null);
    }

    public void start() {
        clear();
        draw();
        present();
    }

    public static void main(String[] args) {
        Canvas canvas = new Canvas(800, 600);
        SwingUtilities.invokeLater(() -> {
            canvas.start();
        });
    }
}
