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
