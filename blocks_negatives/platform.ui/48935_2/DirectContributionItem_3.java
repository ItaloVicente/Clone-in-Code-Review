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
	}

	private Image getImage(String iconURI, LocalResourceManager resourceManager) {
		Image image = null;

		if (iconURI != null && iconURI.length() > 0) {
			ImageDescriptor iconDescriptor = resUtils
					.imageDescriptorFromURI(URI.createURI(iconURI));
			if (iconDescriptor != null) {
				try {
					image = resourceManager.createImage(iconDescriptor);
				} catch (DeviceResourceException e) {
					iconDescriptor = ImageDescriptor
							.getMissingImageDescriptor();
					image = resourceManager.createImage(iconDescriptor);
					Activator.trace(Policy.DEBUG_MENUS,
							"failed to create image " + iconURI, e); //$NON-NLS-1$
				}
			}
		}
		return image;
	}

	private String getDisabledIconURI(MItem toolItem) {
		Object obj = toolItem.getTransientData().get(
				IPresentationEngine.DISABLED_ICON_IMAGE_KEY);
	}

	private void disposeOldImages() {
		if (localResourceManager != null) {
			localResourceManager.dispose();
			localResourceManager = null;
		}
	}

	private Listener getItemListener() {
		if (menuItemListener == null) {
			menuItemListener = new Listener() {
				@Override
				public void handleEvent(Event event) {
					switch (event.type) {
					case SWT.Dispose:
						handleWidgetDispose(event);
						break;
					case SWT.DefaultSelection:
					case SWT.Selection:
						if (event.widget != null) {
							handleWidgetSelection(event);
						}
						break;
					}
				}
			};
		}
		return menuItemListener;
	}

	private void handleWidgetDispose(Event event) {
