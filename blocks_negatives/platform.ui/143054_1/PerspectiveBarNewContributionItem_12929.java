            toolItem = new ToolItem(parent, SWT.PUSH);
            if (image == null || image.isDisposed()) {
                image = WorkbenchImages.getImageDescriptor(
                        IWorkbenchGraphicConstants.IMG_ETOOL_NEW_PAGE)
                        .createImage();
            }
            toolItem.setImage(image);
