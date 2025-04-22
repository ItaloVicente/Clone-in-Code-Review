				parent.addChild(toolbarEntry);
			}
			return;
		}

		String text = null;
		if (element instanceof MItem) {
			text = getToolTipText((MItem) element);
		}
		ImageDescriptor iconDescriptor = null;
		String iconURI = element instanceof MItem ? ((MItem) element).getIconURI() : null;
		if (iconURI != null && iconURI.length() > 0) {
			iconDescriptor = resUtils.imageDescriptorFromURI(URI.createURI(iconURI));
		}
		if (element.getWidget() instanceof ToolItem) {
			ToolItem item = (ToolItem) element.getWidget();
			if (text == null) {
				text = item.getToolTipText();
			}
			if (iconDescriptor == null) {
				Image image = item.getImage();
				if (image != null) {
					iconDescriptor = ImageDescriptor.createFromImage(image);
