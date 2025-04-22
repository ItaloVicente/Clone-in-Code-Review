		IFileTypeProcessor processor = new FileTypeProcessor();
		Iterator<String> it = processor.suffixIterator(filename);
		while (it.hasNext()) {
			String pattern = it.next();
			String key = mappingKeyFor(pattern);
			ImageDescriptor anImage = (ImageDescriptor) extensionImages.get(key);
			if (anImage != null) {
				return anImage;
			}

			FileEditorMapping mapping = getMappingFor(pattern);

			if (mapping != null) {
				String mappingKey = mappingKeyFor(mapping);
				ImageDescriptor mappingImage = (ImageDescriptor) extensionImages.get(key);
				if (mappingImage != null) {
