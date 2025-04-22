		ISelection selection = new StructuredSelection(resource);

		List<Runnable> runnables = new ArrayList<>();
		collectSelectAndRevealRunnables(selection, runnables, page.getViewReferences());
		collectSelectAndRevealRunnables(selection, runnables, page.getEditorReferences());

		if (!runnables.isEmpty()) {
			for (Runnable runnable : runnables) {
				window.getShell().getDisplay().asyncExec(runnable);
