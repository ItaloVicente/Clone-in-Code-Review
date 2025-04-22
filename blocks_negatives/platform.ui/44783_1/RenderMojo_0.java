    /**
     * <p>IconEntry is used to define an icon to rasterize,
     * where to put it and the dimensions to render it at.</p>
     */
    class IconEntry {

        /** The name of the icon minus extension */
        String nameBase;

        /** The input path of the source svg files. */
        File inputPath;

        /**
         * The path rasterized versions of this icon should be written into.
         */
        File outputPath;

        /** The path to a disabled version of the icon (gets desaturated). */
        private File disabledPath;

        /**
         * Creates an IconEntry used for record keeping when
         * rendering a set of SVG icons.
         *
         * @param nameBase the name of the icon file, minus any extension
         * @param inputPath the SVG file that is rendered
         * @param outputPath the path to the rendered icon data
         * @param disabledPath the part to the disabled version of the output icon
         */
        public IconEntry(String nameBase, File inputPath, File outputPath,
                File disabledPath) {
            this.nameBase = nameBase;
            this.inputPath = inputPath;
            this.outputPath = outputPath;
            this.disabledPath = disabledPath;
        }
    }

