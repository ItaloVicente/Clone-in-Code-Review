		FileEditorMapping[] mapping = getMappingForFilename(filename);
		for (int i = 0; i < 2; i++) {
			if (mapping[i] != null) {
				String mappingKey = mappingKeyFor(mapping[i]);
				ImageDescriptor mappingImage = extensionImages.get(key);
				if (mappingImage != null) {
					return mappingImage;
				}
				IEditorDescriptor editor = mapping[i].getDefaultEditor();
				if (editor != null) {
					mappingImage = editor.getImageDescriptor();
					extensionImages.put(mappingKey, mappingImage);
