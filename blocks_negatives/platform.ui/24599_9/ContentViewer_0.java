        if (contentProvider != null) {
            contentProvider.inputChanged(this, getInput(), null);
            contentProvider.dispose();
            contentProvider = null;
        }
        if (labelProvider != null) {
            labelProvider.removeListener(labelProviderListener);
            labelProvider.dispose();
            labelProvider = null;
        }
        input = null;
