
	private boolean validateEditorInput(IEditorInput editorInput) {
		if (editorInput == null
				|| (isInstanceOfInterface(editorInput, "IFileEditorInput") && !editorInput.exists())) { //$NON-NLS-1$
			return false;
		}
		return true;
	}

	private boolean isInstanceOfInterface(Object instance, String className) {
		for (Class<?> cls : instance.getClass().getInterfaces()) {
			if (cls.getName().endsWith(className)) {
				return true;
			}
		}
		return false;
	}
