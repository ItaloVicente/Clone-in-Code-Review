    /**
     * Returns the intensity of an RGB component using the
     * sRGB gamma function.
     *
     * @param val Value to convert.
     * @return Light intensity of the component.
     */
    double voltage_to_intensity_srgb(double val) {
        /* Handle invalid values before doing a gamma transform. */
        if (val < 0.0) {
