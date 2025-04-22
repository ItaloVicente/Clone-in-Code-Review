			}
			@Override
			protected void open(ISelection sel, boolean activate) {
				handleOpen(sel, OpenStrategy.activateOnOpen());
			}
			@Override
			protected void activate(ISelection selection) {
				handleOpen(selection, true);
			}
			private void handleOpen(ISelection selection, boolean activateOnOpen) {
				if (selection instanceof IStructuredSelection)
					if (selection.isEmpty())
						return;
