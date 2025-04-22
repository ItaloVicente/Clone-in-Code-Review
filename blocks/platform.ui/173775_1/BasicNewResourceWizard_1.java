		final ISelection selection = new StructuredSelection(resource);
		Iterator<?> itr = parts.iterator();
		while (itr.hasNext()) {
			IWorkbenchPart part = (IWorkbenchPart) itr.next();

			ISetSelectionTarget target = Adapters.adapt(part, ISetSelectionTarget.class);

			if (target != null) {
				final ISetSelectionTarget finalTarget = target;
				window.getShell().getDisplay().asyncExec(() -> finalTarget.selectReveal(selection));
