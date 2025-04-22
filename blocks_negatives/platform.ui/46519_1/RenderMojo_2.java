
        grayFilter = new GrayscaleFilter();

        desaturator = new HSBAdjustFilter();
        desaturator.setSFactor(0.0f);

        decontrast = new ContrastFilter();
        decontrast.setBrightness(2.9f);
        decontrast.setContrast(0.2f);
        initFilter(decontrast);
    }

    /**
     * Work around the fact that {@link com.jhlabs.image.TransferFilter#initialize()}
     * is not thread-safe.
     * @param filter the filter
     */
    private void initFilter(TransferFilter filter) {
		filter.filter(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
				new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
