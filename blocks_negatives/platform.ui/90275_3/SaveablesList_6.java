				 checkbox.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						dontPromptSelection = checkbox.getSelection();
					}
				 });
