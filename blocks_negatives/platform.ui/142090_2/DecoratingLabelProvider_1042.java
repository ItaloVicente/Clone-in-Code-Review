        provider.dispose();
        if (decorator != null) {
            decorator.dispose();
        }
    }

    /**
     * The <code>DecoratingLabelProvider</code> implementation of this
     * <code>ILabelProvider</code> method returns the image provided
     * by the nested label provider's <code>getImage</code> method,
     * decorated with the decoration provided by the label decorator's
     * <code>decorateImage</code> method.
     */
    @Override
