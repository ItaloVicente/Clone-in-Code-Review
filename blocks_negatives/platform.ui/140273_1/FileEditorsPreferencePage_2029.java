        super.dispose();
        if (imagesToDispose != null) {
            for (Iterator e = imagesToDispose.iterator(); e.hasNext();) {
                ((Image) e.next()).dispose();
            }
            imagesToDispose = null;
        }
        if (editorsToImages != null) {
            for (Iterator e = editorsToImages.values().iterator(); e.hasNext();) {
                ((Image) e.next()).dispose();
            }
            editorsToImages = null;
        }
    }

    /**
     * Hook method to get a page specific preference store. Reimplement this
     * method if a page don't want to use its parent's preference store.
     */
    @Override
