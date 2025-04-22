			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				copyAction.update();
				selectAllAction.update();
			}
		});
