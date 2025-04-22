        if (themeColorRegistry != null) {
            themeColorRegistry.removeListener(themeListener);
            themeColorRegistry.dispose();
        }
        if (themeFontRegistry != null) {
            themeFontRegistry.removeListener(themeListener);
            themeFontRegistry.dispose();
        }
        PrefUtil.getInternalPreferenceStore()
                .removePropertyChangeListener(getPropertyListener());
    }

    @Override
