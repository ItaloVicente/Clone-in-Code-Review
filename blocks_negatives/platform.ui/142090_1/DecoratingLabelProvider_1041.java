        super.addListener(listener);
        provider.addListener(listener);
        if (decorator != null) {
            decorator.addListener(listener);
        }
        listeners.add(listener);
    }

    /**
     * The <code>DecoratingLabelProvider</code> implementation of this <code>IBaseLabelProvider</code> method
     * disposes both the nested label provider and the label decorator.
     */
    @Override
