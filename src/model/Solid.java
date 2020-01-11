package model;

import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.List;

/**
 * Created by Dominik Mandinec
 */

public abstract class Solid {
    protected List<Point3D> vertexBuffer;
    protected List<Integer> indexBuffer;
    protected List<Integer> colors;
    protected Mat4 transMat = new Mat4Identity();
    protected boolean transferable = true;
    private int defaultColor = 0x000000;

    public List<Point3D> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public int getColor(int index) {
        if (colors != null) {
            return colors.get(index);
        }
        return defaultColor;
    }

    public Mat4 getTransMat() {
        return transMat;
    }

    public void setColor(int color) {
        this.defaultColor = color;
    }

    public boolean isTransferable() {
        return transferable;
    }
}
