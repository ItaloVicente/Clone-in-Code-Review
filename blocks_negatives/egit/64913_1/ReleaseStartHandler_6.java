
	private @Nullable Repository getRepository(ExecutionEvent event)
			throws ExecutionException {
		PlatformObject firstElement;
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getCurrentSelection(event);
		if (selection.getFirstElement() instanceof PlotCommit) {
			IWorkbenchPart ap = HandlerUtil.getActivePartChecked(event);
			if (ap instanceof IHistoryView) {
				firstElement = (PlatformObject) ((IHistoryView) ap)
						.getHistoryPage().getInput();
			} else {
				return null;
			}

		} else {
			firstElement = (PlatformObject) selection.getFirstElement();
		}
		return Utils.getAdapter(firstElement, Repository.class);
	}
