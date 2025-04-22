			if (pushExists) {
				MenuItem deleteFetch = new MenuItem(men, SWT.PUSH);
				deleteFetch.setText(UIText.RepositoriesView_RemovePush_menu);
				deleteFetch.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						RepositoryConfig config = node.getRepository()
								.getConfig();
						config.unset("remote", configName, "pushurl"); //$NON-NLS-1$ //$NON-NLS-2$
						config.unset("remote", configName, "push"); //$NON-NLS-1$ //$NON-NLS-2$
						try {
							config.save();
							scheduleRefresh();
						} catch (IOException e1) {
							MessageDialog.openError(getSite().getShell(),
									UIText.RepositoriesView_ErrorHeader, e1
											.getMessage());
						}
					}

				});
			}

