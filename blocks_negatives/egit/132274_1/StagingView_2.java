	@Nullable
	private static Repository getRepository(IEditorPart part) {
		IEditorInput input = part.getEditorInput();
		if (!(input instanceof IURIEditorInput)) {
			return null;
		}
		return AdapterUtils.adapt(input, Repository.class);
	}

