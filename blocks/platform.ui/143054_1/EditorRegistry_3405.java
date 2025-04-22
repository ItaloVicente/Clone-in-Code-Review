		return type.toLowerCase();
	}

	private String mappingKeyFor(FileEditorMapping mapping) {
		return mappingKeyFor(
				mapping.getName() + (mapping.getExtension().length() == 0 ? "" : "." + mapping.getExtension())); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private void rebuildEditorMap() {
		rebuildInternalEditorMap();
		addExternalEditorsToEditorMap();
	}

	private void rebuildInternalEditorMap() {
		mapIDtoEditor = initialIdToEditorMap(mapIDtoEditor.size());

		for (IEditorDescriptor desc : sortedEditorsFromPlugins) {
			mapIDtoEditor.put(desc.getId(), desc);
		}
	}

	@Override
