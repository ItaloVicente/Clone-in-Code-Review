			}
		}

		if (adaptableObject instanceof IURIEditorInput
				&& adapterType == Repository.class) {
			return getRepository((IURIEditorInput) adaptableObject);
		}

		if (adaptableObject instanceof IURIEditorInput
				&& adapterType == File.class) {
			return getFile((IURIEditorInput) adaptableObject);
