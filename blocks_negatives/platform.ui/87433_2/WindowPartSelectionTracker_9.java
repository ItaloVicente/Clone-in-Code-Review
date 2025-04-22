    private final INullSelectionListener selListener = new INullSelectionListener() {
        @Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
            fireSelection(part, selection);
        }
    };
