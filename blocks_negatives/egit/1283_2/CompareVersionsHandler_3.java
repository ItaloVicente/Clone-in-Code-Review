		try {
			IWorkbenchPart part = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActivePart();
			if (!(part instanceof IHistoryView))
				return false;
			IHistoryView view = (IHistoryView) part;
			if (!(view.getHistoryPage().getInput() instanceof IFile))
				return false;
			IStructuredSelection sel = getSelection(null);
			Object[] selected = sel.toArray();
			return selected.length == 2 && selected[0] instanceof RevCommit
					&& selected[1] instanceof RevCommit;
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, false);
