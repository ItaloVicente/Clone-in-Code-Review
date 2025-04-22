        	return true;
        }
    }
    private class CategoryLabelProvider extends LabelProvider implements
            ITableLabelProvider, IActivityManagerListener {

        private LocalResourceManager manager = new LocalResourceManager(
                JFaceResources.getResources());

        private ImageDescriptor lockDescriptor;

        private boolean decorate;

        /**
         * @param decorate
         */
        public CategoryLabelProvider(boolean decorate) {
            this.decorate = decorate;
            lockDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
                    PlatformUI.PLUGIN_ID, "icons/full/ovr16/lock_ovr.png"); //$NON-NLS-1$
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
         *      int)
         */
        public Image getColumnImage(Object element, int columnIndex) {
            ICategory category = (ICategory) element;
            ImageDescriptor descriptor = PlatformUI.getWorkbench()
                    .getActivitySupport().getImageDescriptor(category);
            if (descriptor != null) {
                try {
                    if (decorate) {
                        if (isLocked(category)) {
                            ImageData originalImageData = descriptor
                                    .getImageData();
                            OverlayIcon overlay = new OverlayIcon(
                                    descriptor, lockDescriptor, new Point(
                                            originalImageData.width,
                                            originalImageData.height));
                            return manager.createImage(overlay);
                        }
                    }

                    return manager.createImage(descriptor);
                } catch (DeviceResourceException e) {
                    WorkbenchPlugin.log(e);
                }
            }  
            return null;
        }
      
        @Override
