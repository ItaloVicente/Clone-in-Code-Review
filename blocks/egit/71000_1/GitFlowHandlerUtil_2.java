	static IResource[] gatherResourceToOperateOn(ExecutionEvent event) {
		return getSelectedResources(event);
	}

	static IResource[] getSelectedResources(ExecutionEvent event) {
		IStructuredSelection selection = getSelection(event);
		return SelectionUtils.getSelectedResources(selection);
	}

	static IStructuredSelection getSelection(ExecutionEvent event) {
		if (event == null) {
			throw new IllegalArgumentException("event must not be NULL"); //$NON-NLS-1$
		}
		Object context = event.getApplicationContext();
		if (context instanceof IEvaluationContext) {
			return SelectionUtils.getSelection((IEvaluationContext) context);
		}
		return StructuredSelection.EMPTY;
	}

	static String gatherRevision(ExecutionEvent event) throws IOException {
		final GitFlowRepository gfRepo = GitFlowHandlerUtil
				.getRepository(event);
		Ref exactRef = gfRepo.getRepository()
				.exactRef(gfRepo.getConfig().getDevelopFull());
		if (exactRef == null) {
			throw new IllegalStateException(
					"Gitflow command called on non-Gitflow repository"); //$NON-NLS-1$
		}
		return exactRef.getName();
	}
