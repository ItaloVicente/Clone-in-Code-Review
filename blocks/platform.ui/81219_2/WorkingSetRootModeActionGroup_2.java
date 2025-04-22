							@Override
							public void widgetSelected(SelectionEvent e) {
								action.run();
							}
						});
					}
					if (action == othersWorkingSetAction) {
						othersWorkingSetItem = mi;
					}
					mi.setEnabled(action.isEnabled());
