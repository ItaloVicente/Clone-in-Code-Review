			v.addSelectionChangedListener(new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					getWizard().getContainer().updateButtons();
				}

			});
