			@Override
			public void focusGained(FocusEvent e) {
				if (!useNewLook) {
					/*
					 * Running in an asyncExec because the selectAll() does not
					 * appear to work when using mouse to give focus to text.
					 */
					Display display = filterText.getDisplay();
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							if (!filterText.isDisposed()) {
								if (getInitialText().equals(
										filterText.getText().trim())) {
									filterText.selectAll();
								}
							}
						}
					});
					return;
				}
			}
