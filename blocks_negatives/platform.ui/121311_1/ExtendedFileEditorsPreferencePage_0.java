		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				idePreferenceStore.setValue(IDE.UNASSOCIATED_EDITOR_STRATEGY_PREFERENCE_KEY,
						(String) ((IStructuredSelection) event.getSelection()).getFirstElement());
			}
		});
