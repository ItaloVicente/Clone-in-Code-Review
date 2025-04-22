		page.setDescription(string);

		if (hasImage(selectedObject)) {
			ImageDescriptor descriptor = null;
			if (selectedObject != null) {
				descriptor = selectedObject.getDescriptionImage();
			}

			if (descriptor != null) {
				GridData data = (GridData) descImageCanvas.getLayoutData();
				data.widthHint = SWT.DEFAULT;
				data.heightHint = SWT.DEFAULT;
				Image image = (Image) imageTable.get(descriptor);
				if (image == null) {
					image = descriptor.createImage(false);
					imageTable.put(descriptor, image);
				}
				descImageCanvas.setImage(image);
			}
		} else {
			GridData data = (GridData) descImageCanvas.getLayoutData();
			data.widthHint = 0;
			data.heightHint = 0;
			descImageCanvas.setImage(null);
		}

		descImageCanvas.getParent().layout(true);
		filteredTree.getViewer().getTree().showSelection();

		IWizardContainer container = page.getWizard().getContainer();
		if (container instanceof IWizardContainer2) {
			((IWizardContainer2) container).updateSize();
		}
	}

	private boolean hasImage(IWizardDescriptor selectedObject) {
		if (selectedObject == null) {
