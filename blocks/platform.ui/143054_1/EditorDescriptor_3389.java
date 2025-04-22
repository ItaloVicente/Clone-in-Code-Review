		} else if (getLauncher() != null) {
			return EditorDescriptor.OPEN_EXTERNAL;
		} else if (getFileName() != null) {
			return EditorDescriptor.OPEN_EXTERNAL;
		} else if (getPluginId() != null) {
			return EditorDescriptor.OPEN_INTERNAL;
		} else {
			return 0; // default for system editor
