		getSite().setSelectionProvider(new ISelectionProvider() {

			public void setSelection(ISelection selection) {
			}

			public void removeSelectionChangedListener(
					ISelectionChangedListener listener) {
			}

			public ISelection getSelection() {
				return new StructuredSelection(getCommit());
			}

			public void addSelectionChangedListener(
					ISelectionChangedListener listener) {
			}
		});
