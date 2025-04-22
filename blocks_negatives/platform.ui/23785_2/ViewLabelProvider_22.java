            images = null;
        }
        super.dispose();
    }

    public Image getImage(Object element) {
        if (element instanceof IViewDescriptor) {
            ImageDescriptor desc = ((IViewDescriptor) element)
                    .getImageDescriptor();
            if (desc != null) {
				return cacheImage(desc);
