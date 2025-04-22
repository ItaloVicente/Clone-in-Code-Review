	public void testDefaultImageIsGray() {
		boolean oldState = ActionContributionItem.getUseColorIconsInToolbars();
		try {
			ActionContributionItem.setUseColorIconsInToolbars(false);
			ToolBarManager manager = new ToolBarManager();
			Action action = new Action("Button with Hover") {
			};
			ImageDescriptor descriptor = JFaceResources.getImageRegistry().getDescriptor(Dialog.DLG_IMG_MESSAGE_INFO);
			ImageDescriptor hoverDescriptor = JFaceResources.getImageRegistry()
					.getDescriptor(Dialog.DLG_IMG_MESSAGE_ERROR);
			ImageDescriptor disabledDescriptor = JFaceResources.getImageRegistry()
					.getDescriptor(Dialog.DLG_IMG_MESSAGE_WARNING);
			action.setImageDescriptor(descriptor);
			action.setHoverImageDescriptor(hoverDescriptor);
			action.setDisabledImageDescriptor(disabledDescriptor);
			manager.add(action);
			ToolBar toolBar = manager.createControl(createComposite());
			ToolItem[] items = toolBar.getItems();
			assertEquals(1, items.length);

			ToolItem item = items[0];
			assertTrue(
					Arrays.equals(hoverDescriptor.getImageData(100).data, item.getHotImage().getImageData(100).data));
			assertTrue(Arrays.equals(disabledDescriptor.getImageData(100).data,
					item.getDisabledImage().getImageData(100).data));
			ImageData imageData = item.getImage().getImageData(100);
			for (int x = 0; x < imageData.width; x++) {
				for (int y = 0; y < imageData.height; y++) {
					if (imageData.getAlpha(x, y) == 255) {
						int rgb = imageData.getPixel(x, y);
						int r = rgb & 0xFF;
						int g = (rgb >> 8) & 0xFF;
						int b = (rgb >> 16) & 0xFF;
						assertEquals(r, g);
						assertEquals(g, b);
					}
				}

			}
		} finally {
			ActionContributionItem.setUseColorIconsInToolbars(oldState);
		}
	}

	public void testActionImagesAreSet() {
		boolean oldState = ActionContributionItem.getUseColorIconsInToolbars();
		try {
			ActionContributionItem.setUseColorIconsInToolbars(true);
			ToolBarManager manager = new ToolBarManager();
			Action action = new Action("Button with Hover") {
			};
			ImageDescriptor descriptor = JFaceResources.getImageRegistry().getDescriptor(Dialog.DLG_IMG_MESSAGE_INFO);
			ImageDescriptor hoverDescriptor = JFaceResources.getImageRegistry()
					.getDescriptor(Dialog.DLG_IMG_MESSAGE_ERROR);
			ImageDescriptor disabledDescriptor = JFaceResources.getImageRegistry()
					.getDescriptor(Dialog.DLG_IMG_MESSAGE_WARNING);
			action.setImageDescriptor(descriptor);
			action.setHoverImageDescriptor(hoverDescriptor);
			action.setDisabledImageDescriptor(disabledDescriptor);
			manager.add(action);
			ToolBar toolBar = manager.createControl(createComposite());
			ToolItem[] items = toolBar.getItems();
			assertEquals(1, items.length);

			ToolItem item = items[0];
			assertTrue(Arrays.equals(descriptor.getImageData(100).data, item.getImage().getImageData(100).data));
			assertTrue(
					Arrays.equals(hoverDescriptor.getImageData(100).data, item.getHotImage().getImageData(100).data));
			assertTrue(Arrays.equals(disabledDescriptor.getImageData(100).data,
					item.getDisabledImage().getImageData(100).data));
		} finally {
			ActionContributionItem.setUseColorIconsInToolbars(oldState);
		}
	}

	public void testMissingIsSet() {
		boolean oldState = ActionContributionItem.getUseColorIconsInToolbars();
		try {
			ActionContributionItem.setUseColorIconsInToolbars(true);
			ToolBarManager manager = new ToolBarManager();
			Action action = new Action("Button with Missing") {
			};
			action.setDisabledImageDescriptor(
					JFaceResources.getImageRegistry().getDescriptor(Dialog.DLG_IMG_MESSAGE_WARNING));
			manager.add(action);
			ToolBar toolBar = manager.createControl(createComposite());
			ToolItem[] items = toolBar.getItems();
			assertEquals(1, items.length);

			ToolItem item = items[0];
			assertNotNull(item.getImage());
			Image img = ImageDescriptor.getMissingImageDescriptor().createImage();
			byte[] data = img.getImageData().data;
			img.dispose();
			assertTrue(Arrays.equals(data, item.getImage().getImageData(100).data));
			assertNull(item.getHotImage());
			assertNotNull(item.getDisabledImage());
		} finally {
			ActionContributionItem.setUseColorIconsInToolbars(oldState);
		}
	}

