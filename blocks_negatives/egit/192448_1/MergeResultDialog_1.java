
		new OpenAndLinkWithEditorHelper(viewer) {
			@Override
			protected void linkToEditor(ISelection selection) {

			}
			@Override
			protected void open(ISelection selection, boolean activate) {
				handleOpen(selection, OpenStrategy.activateOnOpen());
			}
			@Override
			protected void activate(ISelection selection) {
				handleOpen(selection, true);
			}
			private void handleOpen(ISelection selection, boolean activateOnOpen) {
				if (selection instanceof IStructuredSelection)
					for (Object element : ((IStructuredSelection) selection)
							.toArray())
						if (element instanceof RepositoryCommit)
							CommitEditor.openQuiet((RepositoryCommit) element, activateOnOpen);
			}
		};
