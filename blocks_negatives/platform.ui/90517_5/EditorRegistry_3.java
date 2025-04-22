        IContentType [] contentTypes = Platform.getContentTypeManager().getAllContentTypes();
        for (int i = 0; i < contentTypes.length; i++) {
			IContentType type = contentTypes[i];
			String [] extensions = type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
			for (int j = 0; j < extensions.length; j++) {
				String extension = extensions[j];
