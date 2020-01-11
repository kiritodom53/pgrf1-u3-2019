package view;

import javax.swing.*;

/**
 * Created by Dominik Mandinec
 */

public class Toolbar {
    private JToolBar toolBar;
    private JButton btnPer;
    private JButton btnOr;
    private JButton btnReset;
    private JButton btnCube;
    private JButton btnTetra;
    private JButton btnGrid;
    private JButton btnPyramid;
    private JButton btnBicubicGrid;

    private JRadioButton rbBezier;
    private JRadioButton rbCoons;
    private JRadioButton rbFerguson;

    private ButtonGroup bg;

    public ButtonGroup getBg() {
        return bg;
    }

    public Toolbar() {
        toolBar = new JToolBar();

        btnPer = new JButton();
        btnOr = new JButton();
        btnReset = new JButton();
        btnCube = new JButton();
        btnTetra = new JButton();
        btnGrid = new JButton();
        btnPyramid = new JButton();
        btnBicubicGrid = new JButton();

        bg = new ButtonGroup();

        rbBezier = new JRadioButton();
        rbCoons = new JRadioButton();
        rbFerguson = new JRadioButton();

        bg.add(rbBezier);
        bg.add(rbCoons);
        bg.add(rbFerguson);

        btnPer.setText("Perspectivní");
        btnOr.setText("Ortogonální");
        btnReset.setText("Reset");
        btnCube.setText("Cube");
        btnTetra.setText("Tetrahedron");
        btnGrid.setText("Grid");
        btnPyramid.setText("Pyramid");
        btnBicubicGrid.setText("Bikubická plocha");

        rbBezier.setText("Bezier");
        rbCoons.setText("Coons");
        rbFerguson.setText("Ferguson");

        btnPer.setFocusable(false);
        btnOr.setFocusable(false);
        btnReset.setFocusable(false);
        btnCube.setFocusable(false);
        btnTetra.setFocusable(false);
        btnGrid.setFocusable(false);
        btnPyramid.setFocusable(false);
        toolBar.setFocusable(false);
        btnBicubicGrid.setFocusable(false);
        rbBezier.setFocusable(false);
        rbCoons.setFocusable(false);
        rbFerguson.setFocusable(false);

        this.initComponents();
    }

    private void initComponents() {
        toolBar.add(btnReset);
        toolBar.add(btnPer);
        toolBar.add(btnOr);
        toolBar.add(btnCube);
        toolBar.add(btnTetra);
        toolBar.add(btnGrid);
        toolBar.add(btnPyramid);
        toolBar.add(rbBezier);
        toolBar.add(rbCoons);
        toolBar.add(rbFerguson);
        toolBar.add(btnBicubicGrid);
    }

    public JRadioButton getRbBezier() {
        return rbBezier;
    }

    public JRadioButton getRbCoons() {
        return rbCoons;
    }

    public JRadioButton getRbFerguson() {
        return rbFerguson;
    }

    public JToolBar getToolBar() {
        return toolBar;
    }

    public JButton getBtnBicubicGrid() {
        return btnBicubicGrid;
    }

    public JButton getBtnPer() {
        return btnPer;
    }

    public JButton getBtnOr() {
        return btnOr;
    }

    public JButton getBtnReset() {
        return btnReset;
    }

    public JButton getBtnCube() {
        return btnCube;
    }

    public JButton getBtnTetra() {
        return btnTetra;
    }

    public JButton getBtnGrid() {
        return btnGrid;
    }

    public JButton getBtnPyramid() {
        return btnPyramid;
    }
}
