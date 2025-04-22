			if (image == null || image.isDisposed()) {
				createImage();
			}
			toolItem.setImage(image);

			toolItem.setToolTipText(
					NLS.bind(WorkbenchMessages.PerspectiveBarContributionItem_toolTip, perspective.getLabel()));
			toolItem.addSelectionListener(widgetSelectedAdapter(event -> select()));
			toolItem.setData(this); // TODO review need for this
			update();
		}
	}

	private void createImage() {
		ImageDescriptor imageDescriptor = perspective.getImageDescriptor();
		if (imageDescriptor != null) {
			image = imageDescriptor.createImage();
		} else {
			image = WorkbenchImages.getImageDescriptor(ISharedImages.IMG_ETOOL_DEF_PERSPECTIVE).createImage();
		}
	}

	Image getImage() {
		if (image == null) {
			createImage();
		}
		return image;
	}

	public void select() {
		if (workbenchPage.getPerspective() != perspective) {
			workbenchPage.setPerspective(perspective);
		} else {
