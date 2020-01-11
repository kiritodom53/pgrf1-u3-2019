package model;

import transforms.Point3D;

import java.util.ArrayList;

/**
 * Created by Dominik Mandinec
 */

public class Pyramid extends Solid {
    public Pyramid() {
        indexBuffer = new ArrayList<>();
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(3);
        indexBuffer.add(4);
        indexBuffer.add(1);
        indexBuffer.add(4);
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(0);
        indexBuffer.add(2);
        indexBuffer.add(0);
        indexBuffer.add(3);
        indexBuffer.add(0);
        indexBuffer.add(4);

        vertexBuffer = new ArrayList<>();
        vertexBuffer.add(new Point3D(0, 0, 4));     //0
        vertexBuffer.add(new Point3D(-2, -2, -1));   //1
        vertexBuffer.add(new Point3D(-2, 2, -1));    //2
        vertexBuffer.add(new Point3D(2, 2, -1));     //3
        vertexBuffer.add(new Point3D(2, -2, -1));    //4

        setColor(0xff7400);
    }
}
