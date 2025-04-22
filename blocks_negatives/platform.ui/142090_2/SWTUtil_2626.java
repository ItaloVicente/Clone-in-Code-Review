    /**
     * @param rgb1
     * @param rgb2
     * @param ratio
     * @return the RGB object
     */
    public static RGB mix(RGB rgb1, RGB rgb2, double ratio) {
        return new RGB(interp(rgb1.red, rgb2.red, ratio),
                interp(rgb1.green, rgb2.green, ratio),
                interp(rgb1.blue, rgb2.blue, ratio));
    }
