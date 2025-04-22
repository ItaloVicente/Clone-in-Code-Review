	private static void collectSelectAndRevealRunnables(ISelection selection, List<Runnable> runnables,
			IWorkbenchPartReference[] partRefs) {
		for (IWorkbenchPartReference partRef : partRefs) {
			IWorkbenchPart part = partRef.getPart(false);
			if (part != null) {
				ISelectionProvider selectionProvider = part.getSite().getSelectionProvider();
				if (selectionProvider != null) {
					ISetSelectionTarget selectionTarget = Adapters.adapt(part, ISetSelectionTarget.class);
					if (selectionTarget != null) {
						runnables.add(() -> {
							ISelection oldSelection = selectionProvider.getSelection();
							selectionTarget.selectReveal(selection);

							if (!oldSelection.isEmpty()) {
								ISelection newSelection = selectionProvider.getSelection();
								if (!selection.equals(newSelection)) {
									selectionTarget.selectReveal(oldSelection);
								}
							}
						});
					}
				}
