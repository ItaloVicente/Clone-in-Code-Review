        } else if (element instanceof IViewCategory) {
            ImageDescriptor desc = WorkbenchImages
                    .getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER);
            return cacheImage(desc);
        }
        return null;
    }

    @Override
	public String getText(Object element) {
        String label = WorkbenchMessages.ViewLabel_unknown;
        if (element instanceof IViewCategory) {
			label = ((IViewCategory) element).getLabel();
		} else if (element instanceof IViewDescriptor) {
			label = ((IViewDescriptor) element).getLabel();
