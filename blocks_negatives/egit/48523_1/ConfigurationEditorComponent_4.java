			if (changeablePath) {
				Button selectPath = new Button(locationPanel, SWT.PUSH);
				selectPath
						.setText(UIText.ConfigurationEditorComponent_BrowseForPrefix);
				selectPath.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						DirectoryDialog dialog = new DirectoryDialog(
								getShell(), SWT.OPEN);
						dialog.setText(UIText.ConfigurationEditorComponent_SelectGitInstallation);
						String file = dialog.open();
						if (file != null) {
							File etc = new File(file, "etc"); //$NON-NLS-1$
							File bin = new File(file, "bin"); //$NON-NLS-1$
							if (!new File(etc, "gitconfig").exists() //$NON-NLS-1$
									&& !new File(bin, "git").exists() //$NON-NLS-1$
									&& (!System
									!new File(bin, "git.exe").exists())) { //$NON-NLS-1$
								MessageDialog
										.open(SWT.ERROR,
												getShell(),
												UIText.ConfigurationEditorComponent_GitPrefixSelectionErrorTitle,
												UIText.ConfigurationEditorComponent_GitPrefixSelectionErrorMessage,
												SWT.NONE);
								return;
							}
							location.setText(file);
							try {
								IEclipsePreferences node = InstanceScope.INSTANCE
										.getNode(org.eclipse.egit.core.Activator
												.getPluginId());
								node.put(GitCorePreferences.core_gitPrefix,
										file);
								node.flush();
								setChangeSystemPrefix(file);
							} catch (Exception e1) {
								Activator
										.logError(
												UIText.ConfigurationEditorComponent_CannotChangeGitPrefixError,
												e1);
							}
						}
					}
				});
			}
