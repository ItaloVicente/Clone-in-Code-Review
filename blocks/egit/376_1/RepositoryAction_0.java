	@Override
	protected IStructuredSelection getSelection() {
		if (getTargetPart().getSite() != null) {
			final ISelection partSelection = getTargetPart().getSite()
					.getSelectionProvider().getSelection();
			if (partSelection != null) {
				if (partSelection instanceof IStructuredSelection) {
					return (IStructuredSelection) partSelection;
				} else if (partSelection instanceof ITextSelection) {
					IResource resource = ResourceUtil.getResource(getTargetPart()
							.getSite().getWorkbenchWindow().getActivePage()
							.getActiveEditor().getEditorInput());
					return new StructuredSelection(resource);
				}
			}
		}
		return super.getSelection();
	}

