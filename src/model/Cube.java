package model;

import transforms.Point3D;

import java.util.ArrayList;

/**
 * Created by Dominik Mandinec
 */

public class Cube extends Solid {
    public Cube() {
        indexBuffer = new ArrayList<>();

        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(3);
        indexBuffer.add(0);
        indexBuffer.add(0);
        indexBuffer.add(4);
        indexBuffer.add(4);
        indexBuffer.add(5);
        indexBuffer.add(5);
        indexBuffer.add(6);
        indexBuffer.add(6);
        indexBuffer.add(7);
        indexBuffer.add(7);
        indexBuffer.add(4);
        indexBuffer.add(1);
        indexBuffer.add(5);
        indexBuffer.add(2);
        indexBuffer.add(6);
        indexBuffer.add(3);
        indexBuffer.add(7);

        vertexBuffer = new ArrayList<>();
        vertexBuffer.add(new Point3D(-2, -2, -2));
        vertexBuffer.add(new Point3D(-2, 2, -2));
        vertexBuffer.add(new Point3D(2, 2, -2));
        vertexBuffer.add(new Point3D(2, -2, -2));

        vertexBuffer.add(new Point3D(-2, -2, 2));
        vertexBuffer.add(new Point3D(-2, 2, 2));
        vertexBuffer.add(new Point3D(2, 2, 2));
        vertexBuffer.add(new Point3D(2, -2, 2));
        setColor(0x00ffff);
    }
}
