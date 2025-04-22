			return null;
		} else if (element instanceof String) {
			Image image = imageMap.get(FOLDER_ICON);
			if (image == null) {
				ImageDescriptor desc = WorkbenchImages
						.getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER);
				imageMap.put(FOLDER_ICON, desc.createImage());
