    /**
     * Returns a measure of the lightness in the perceptual colourspace
     * IPT.
     *
     * @param color The colour in sRGB
     * @return Lightness in IPT space.
     */
    double lightness(RGB color) {
        double r = voltage_to_intensity_srgb(color.red / 255.0);
        double g = voltage_to_intensity_srgb(color.green / 255.0);
        double b = voltage_to_intensity_srgb(color.blue / 255.0);
        double l = (0.3139 * r) + (0.6395 * g) + (0.0466 * b);
        double m = (0.1516 * r) + (0.7482 * g) + (0.1000 * b);
        double s = (0.0177 * r) + (0.1095 * g) + (0.8729 * b);
        double lp, mp, sp;
