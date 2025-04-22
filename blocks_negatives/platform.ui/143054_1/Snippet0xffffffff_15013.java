					new PropertyChangeListener() {
						@Override
						public void propertyChange(PropertyChangeEvent evt) {
							display.asyncExec(new Runnable() {
								@Override
								public void run() {
									final String newName = viewModel.person.getName();
									name.setText(newName);
								}
							});
						}
					});
