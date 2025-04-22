		} else if (typedImage instanceof Map) {
			final Map styleMap = (Map) typedImage;
			styleMap.put(style, descriptor);
		} else if (typedImage instanceof ImageDescriptor || typedImage == null) {
			final Map styleMap = new HashMap();
			styleMap.put(null, typedImage);
			styleMap.put(style, descriptor);
			images[type] = styleMap;
