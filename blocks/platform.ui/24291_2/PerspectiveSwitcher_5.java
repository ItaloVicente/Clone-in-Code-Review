
	private void updateToolItem(ToolItem ti, String attName, Object newValue) {
		boolean showText = PrefUtil.getAPIPreferenceStore().getBoolean(
				IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR);
		if (showText && UIEvents.UILabel.LABEL.equals(attName)) {
			String newName = (String) newValue;
			ti.setText(newName);
		} else if (UIEvents.UILabel.TOOLTIP.equals(attName)) {
			String newTTip = (String) newValue;
			ti.setToolTipText(newTTip);
		} else if (UIEvents.UILabel.ICONURI.equals(attName)) {
			Image currentImage = ti.getImage();
			String uri = (String) newValue;
			URL url = null;
			try {
				url = new URL(uri);
				ImageDescriptor descriptor = ImageDescriptor.createFromURL(url);
				if (descriptor == null) {
					ti.setImage(null);
				} else
					ti.setImage(descriptor.createImage());
			} catch (IOException e) {
				ti.setImage(null);
				logger.warn(e);
			} finally {
				if (currentImage != null)
					currentImage.dispose();
			}
		}
	}
