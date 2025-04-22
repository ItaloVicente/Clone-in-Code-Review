    /**
     * <p>Creates an IconEntry during the icon gather operation.</p>
     *
     * @param input the source of the icon file (SVG document)
     * @param outputPath the path of the rasterized version to generate
     * @param disabledPath the path of the disabled (desaturated) icon, if one is required
     *
     * @return an IconEntry describing the rendering operation
     */
    public IconEntry createIcon(File input, File outputPath, File disabledPath) {
        String name = input.getName();
        String[] split = name.split("\\.(?=[^\\.]+$)");

        IconEntry def = new IconEntry(split[0], input, outputPath, disabledPath);

        return def;
    }

