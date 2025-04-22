		Control contents = super.createContents(parent);

		initializeControls();
		validatePage();
		resourceGroup.setFocus();
		setTitle(IDEWorkbenchMessages.SaveAsDialog_title);
		ImageDescriptor descriptor = IDEInternalWorkbenchImages
				.getImageDescriptor(IDEInternalWorkbenchImages.IMG_DLGBAN_SAVEAS_DLG);
		if (descriptor != null) {
			dlgTitleImage = descriptor.createImage();
			setTitleImage(dlgTitleImage);
		}
		setMessage(IDEWorkbenchMessages.SaveAsDialog_message);

		return contents;
	}

	@Override
