		getSite().setSelectionProvider(this);

		tv.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {

				IStructuredSelection ssel = (IStructuredSelection) event
						.getSelection();
				if (ssel.size() == 1) {
					setSelection(new StructuredSelection(ssel.getFirstElement()));
				} else {
					setSelection(new StructuredSelection());
				}

			}
		});

