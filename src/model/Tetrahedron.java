package model;

import transforms.Point3D;

import java.util.ArrayList;

/**
 * Created by Dominik Mandinec
 */

public class Tetrahedron extends Solid {
    public Tetrahedron() {
        vertexBuffer = new ArrayList<>();
        vertexBuffer.add(new Point3D(-1, -1, -1));
        vertexBuffer.add(new Point3D(-1, -1, 2));
        vertexBuffer.add(new Point3D(2, -1, -1));
        vertexBuffer.add(new Point3D(-1, 2, -1));

        indexBuffer = new ArrayList<>();
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(0);
        indexBuffer.add(3);
        indexBuffer.add(0);
        indexBuffer.add(2);
        indexBuffer.add(1);
        indexBuffer.add(3);
        indexBuffer.add(3);
        indexBuffer.add(2);
        indexBuffer.add(1);
        indexBuffer.add(2);

        setColor(0xff00ff);
    }
}
