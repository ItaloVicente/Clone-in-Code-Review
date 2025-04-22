		private IResource getResource(IEditorPart part) {
			IEditorInput input = part.getEditorInput();
			if (input instanceof IFileEditorInput) {
				return ((IFileEditorInput) input).getFile();
			} else {
				return CommonUtils.getAdapter(input, IResource.class);
			}
		}

