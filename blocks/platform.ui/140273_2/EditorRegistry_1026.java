		String key = mappingKeyFor(ext);
		return typeEditorMappings.get(key);
	}

	private FileEditorMapping[] getMappingForFilename(String filename) {
		FileEditorMapping[] mapping = new FileEditorMapping[2];

		mapping[0] = getMappingFor(filename);

		int index = filename.lastIndexOf('.');
		if (index > -1) {
			String extension = filename.substring(index);
			mapping[1] = getMappingFor("*" + extension); //$NON-NLS-1$
		}

		return mapping;
	}

	public IEditorDescriptor[] getSortedEditorsFromOS() {
