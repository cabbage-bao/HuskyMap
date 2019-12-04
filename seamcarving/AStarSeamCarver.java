package seamcarving;

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class AStarSeamCarver implements SeamCarver {
    private Picture picture;

    public AStarSeamCarver(Picture picture) {
        if (picture == null) {
            throw new NullPointerException("Picture cannot be null.");
        }
        this.picture = new Picture(picture);
    }

    public Picture picture() {
        return new Picture(picture);
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public Color get(int x, int y) {
        return picture.get(x, y);
    }

    public double energy(int x, int y) {
        if (x >= width() || y >= height() || x < 0 || y < 0) {
            throw new IllegalArgumentException();
        }
        // return x + y
        return Math.sqrt(xGrad(x, y) + yGrad(x, y));
    }

    // helper function to return xGrad
    private double xGrad(int x, int y) {
        if (x >= width() || x < 0) {
            throw new IllegalArgumentException();
        }
        // deal with cases when x == 0 or x == width()
        Color left;
        Color right;
        if (width() == 1) {
            return 0.0;
        }
        if (x == 0) {
            left = picture.get(width() - 1, y);
            right = picture.get(x + 1, y);
        } else if (x == width() - 1) {
            left = picture.get(width() - 2, y);
            right = picture.get(0, y);
        } else {
            left = picture.get(x - 1, y);
            right = picture.get(x + 1, y);
        }

        return grad(left, right);
    }

    // helper dunction to return yGrad
    private double yGrad(int x, int y) {
        if (y >= height() || y < 0) {
            throw new IllegalArgumentException();
        }
        // deal with cases when x == 0 or x == width()
        Color up;
        Color down;
        if (height() == 1) {
            return 0.0;
        }
        if (y == 0) {
            up = picture.get(x, height() - 1);
            down = picture.get(x, y + 1);
        } else if (y == height() - 1) {
            up = picture.get(x, height() - 2);
            down = picture.get(x, 0);
        } else {
            up = picture.get(x, y - 1);
            down = picture.get(x, y + 1);
        }

        return grad(up, down);
    }

    // helper function to calculate the grad value
    private double grad(Color x, Color y) {
        int deltaR = x.getRed() - y.getRed();
        int deltaG = x.getGreen() - y.getGreen();
        int deltaB = x.getBlue() - y.getBlue();

        return deltaR * deltaR + deltaB * deltaB + deltaG * deltaG;
    }


    public int[] findHorizontalSeam() {

        // create a 3-D array to store the path information
        int[][] edgeTo = new int[width()][height() + 2];
        double[][] dp = new double[width()][height() + 2];

        // array initialization
        for (int i = 0; i < width(); i++) {
            dp[i][0] = Double.MAX_VALUE;
            dp[i][height() + 1] = Double.MAX_VALUE;
        }

        // array initialization
        for (int i = 1; i <= height(); i++) {
            dp[0][i] = energy(0, i - 1);
        }

        for (int col = 1; col < width(); col++) {
            for (int row = 1; row <= height(); row++) {
                double min;                                           //find the min sum of the previous path
                if (dp[col - 1][row + 1] < dp[col - 1][row]) {
                    min = dp[col - 1][row + 1];
                    edgeTo[col][row] = row + 1;
                } else {
                    min = dp[col - 1][row];
                    edgeTo[col][row] = row;
                }
                if (dp[col - 1][row - 1] < min) {
                    min = dp[col - 1][row - 1];
                    edgeTo[col][row] = row - 1;
                }
                dp[col][row] = min + energy(col, row - 1);
            }
        }
        // Index represents the path which has the min total value
        double tempMin = Double.MAX_VALUE;
        int index = 0;
        for (int i = 1; i <= height(); i++) {
            if (dp[width() - 1][i] < tempMin) {
                tempMin = dp[width() - 1][i];
                index = i;
            }
        }

        //retrieve all the index in each col to obtain the minimum path by edgeTo
        int[] result = new int[width()];
        result[width() - 1] = index - 1;

        for (int i = width() - 2; i >= 0; i--) {
            result[i] = edgeTo[i + 1][index] - 1;
            index = result[i] + 1;
        }
        return result;
    }

    public int[] findVerticalSeam() {
        // create a 3-D array to store the path information
        int[][] edgeTo = new int[width() + 2][height()];
        double[][] dp = new double[width() + 2][height()];

        // array initialization
        for (int i = 0; i < height(); i++) {
            dp[0][i] = Double.MAX_VALUE;
            dp[width() + 1][i] = Double.MAX_VALUE;
        }

        // array initialization for first row
        for (int i = 1; i <= width(); i++) {
            dp[i][0] = energy(i - 1, 0);
        }

        for (int row = 1; row < height(); row++) {
            for (int col = 1; col <= width(); col++) {
                double min;                                           //find the min sum of the previous path
                if (dp[col - 1][row - 1] < dp[col][row - 1]) {
                    min = dp[col - 1][row - 1];
                    edgeTo[col][row] = col - 1;
                } else {
                    min = dp[col][row - 1];
                    edgeTo[col][row] = col;
                }
                if (dp[col + 1][row - 1] < min) {
                    min = dp[col + 1][row - 1];
                    edgeTo[col][row] = col + 1;
                }
                dp[col][row] = min + energy(col - 1, row);
            }
        }
        // Index represents the path which has the min total value
        double tempMin = Double.MAX_VALUE;
        int index = 0;
        for (int i = 1; i <= width(); i++) {
            if (dp[i][height() - 1] < tempMin) {
                tempMin = dp[i][height() - 1];
                index = i;
            }
        }

        //retrieve all the index in each col to obtain the minimum path by edgeTo
        int[] result = new int[height()];
        result[height() - 1] = index - 1;

        for (int i = height() - 2; i >= 0; i--) {
            result[i] = edgeTo[index][i + 1] - 1;
            index = result[i] + 1;
        }
        return result;
    }
}
