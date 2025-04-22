		configsTable.getColumnViewerEditor().addEditorActivationListener(
				new ColumnViewerEditorActivationListener() {

					@Override
					public void beforeEditorActivated(
							ColumnViewerEditorActivationEvent event) {
					}

					@Override
					public void afterEditorActivated(
							ColumnViewerEditorActivationEvent event) {
					}

					@Override
					public void beforeEditorDeactivated(
							ColumnViewerEditorDeactivationEvent event) {
					}

					@Override
					public void afterEditorDeactivated(
							ColumnViewerEditorDeactivationEvent event) {
						setMessage(null);
						updateButtonEnablement();
						getButton(OK).setEnabled(true);
					}
				});
