		if (key.equals(IContentOutlinePage.class)) {
			IEditorInput input = getEditorInput();
			if (input instanceof IFileEditorInput) {
				page = new ReadmeContentOutlinePage(((IFileEditorInput) input).getFile());
				return (T) page;
			}
		}
		return super.getAdapter(key);
	}

	@Override
