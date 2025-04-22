			throws PartInitException {
		try {
			return getEditorDescriptor(name, true, false);
		} catch (OperationCanceledException ex) {
			throw new PartInitException(ex.getMessage(), ex);
		}
