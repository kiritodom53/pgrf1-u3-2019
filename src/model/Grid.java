package model;

import transforms.Point3D;

import java.util.ArrayList;

/**
 * Created by Dominik Mandinec
 */

public class Grid extends Solid {
    public Grid(int n, int m) {
        vertexBuffer = new ArrayList<>();
        indexBuffer = new ArrayList<>();
        setColor(0xB3F800);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                vertexBuffer.add(new Point3D(i * 1f, j * 1f, 0));
            }
        }

        for (int i = 0; i < n; i++) {
            indexBuffer.add(i);
            indexBuffer.add(i + (n * (m - 1)));
            indexBuffer.add(i * m);
            indexBuffer.add(i * m + n - 1);
        }
    }
}
