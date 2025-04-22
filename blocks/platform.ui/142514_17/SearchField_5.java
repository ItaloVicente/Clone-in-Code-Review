	private Button createButton(Composite parent) {
		Button res = new Button(parent, SWT.PUSH | SWT.FLAT);
		try {
			res.setText(quickAccessCommand.getName());
		} catch (NotDefinedException e) {
			WorkbenchPlugin.log(e);
		}
		ImageDescriptor imageDescriptor = commandImageService.getImageDescriptor(quickAccessCommand.getId());
		if (imageDescriptor != null) {
			Image image = imageDescriptor.createImage();
			res.setImage(image);
			res.addDisposeListener(e -> image.dispose());
		}
		return res;
