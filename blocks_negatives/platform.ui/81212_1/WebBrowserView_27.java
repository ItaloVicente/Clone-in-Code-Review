		listener = new ISelectionListener() {
			@Override
			public void selectionChanged(IWorkbenchPart part,
					ISelection selection) {
				onSelectionChange(selection);
			}
		};
