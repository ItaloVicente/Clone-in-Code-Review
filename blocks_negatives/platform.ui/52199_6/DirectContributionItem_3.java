		item.setSelection(model.isSelected());
		item.setEnabled(model.isEnabled());
	}

	private void updateIcons() {
		if (!(widget instanceof Item)) {
			return;
		}
		Item item = (Item) widget;
		String disabledURI = getDisabledIconURI(model);
		if (!iconURI.equals(item.getData(ICON_URI))
				|| !disabledURI.equals(item.getData(DISABLED_URI))) {
			LocalResourceManager resourceManager = new LocalResourceManager(
					JFaceResources.getResources());
			Image iconImage = getImage(iconURI, resourceManager);
			item.setImage(iconImage);
			item.setData(ICON_URI, iconURI);
			if (item instanceof ToolItem) {
				iconImage = getImage(disabledURI, resourceManager);
				((ToolItem) item).setDisabledImage(iconImage);
				item.setData(DISABLED_URI, disabledURI);
			}
			disposeOldImages();
			localResourceManager = resourceManager;
		}
