package render;

import model.Solid;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by Dominik Mandinec
 */

public class Render {
    private BufferedImage img;
    private Mat4 view;
    private Mat4 proj;
    private Mat4 model;

    public void setBufferedImage(BufferedImage img) {
        this.img = img;
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProj(Mat4 proj) {
        this.proj = proj;
    }

    /**
     * Opravená funkce na ořezávání - dodělání pro zápočet
     */
    private Boolean orez(Point3D point){
        return -point.getW() <= point.getY() && -point.getW() <= point.getX() && point.getY() <= point.getW() && point.getX() <= point.getW() && point.getZ() >= 0 && point.getZ() <= point.getW();
    }

    public void draw(Solid solid, Mat4 model) {
        if (solid.isTransferable()) {
            this.model = solid.getTransMat().mul(model);
        } else {
            this.model = new Mat4Identity();
        }

        final Mat4 finalTransform = this.model.mul(view).mul(proj);
        List<Integer> indexs = solid.getIndexBuffer();

        for (int i = 0; i < indexs.size(); i += 2) {
            int ibA = solid.getIndexBuffer().get(i);
            int ibB = solid.getIndexBuffer().get(i + 1);

            Point3D vbA = solid.getVertexBuffer().get(ibA);
            Point3D vbB = solid.getVertexBuffer().get(ibB);

            vbA = vbA.mul(finalTransform);
            vbB = vbB.mul(finalTransform);

            Vec3D vectorA = null;
            Vec3D vectorB = null;

            // Podmínka na ořezávání - dodělání pro zápočet
            if (orez(vbA) && orez(vbB)){
                if (vbA.dehomog().isPresent()) {
                    vectorA = vbA.dehomog().get();
                }

                if (vbA.dehomog().isPresent()) {
                    vectorB = vbB.dehomog().get();
                }

                int x1 = (int) ((1 + vectorA.getX()) * (img.getWidth() - 1) / 2);
                int y1 = (int) ((1 - vectorA.getY()) * (img.getHeight() - 1) / 2);

                int x2 = (int) ((1 + vectorB.getX()) * (img.getWidth() - 1) / 2);
                int y2 = (int) ((1 - vectorB.getY()) * (img.getHeight() - 1) / 2);

                Graphics g = img.getGraphics();
                g.setColor(new Color(solid.getColor(i / 2)));
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }
}
