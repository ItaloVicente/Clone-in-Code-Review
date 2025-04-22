    private final INullSelectionListener postSelListener = new INullSelectionListener() {
        @Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
            firePostSelection(part, selection);
        }
    };
