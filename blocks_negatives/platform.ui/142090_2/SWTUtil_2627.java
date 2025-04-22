    private static int interp(int i1, int i2, double ratio) {
        int result = (int)(i1 * ratio + i2 * (1.0d - ratio));
        if (result < 0) result = 0;
        if (result > 255) result = 255;
        return result;
    }
