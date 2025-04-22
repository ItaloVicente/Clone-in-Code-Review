			final Button change = new Button(buttonBar, SWT.PUSH);
			change.setText(UIText.ConfigureUriPage_Change_button);
			change.setEnabled(false);

			change.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					final URIish oldUri = (URIish) ((IStructuredSelection) tv
							.getSelection()).getFirstElement();
					SelectUriWizard selectWizard = new SelectUriWizard(false,
							oldUri.toPrivateString());
					WizardDialog dlg = new WizardDialog(getShell(),
							selectWizard);
					dlg.setHelpAvailable(false);
					if (dlg.open() == Window.OK) {
						URIish newUri = selectWizard.getUri();
						if (newUri.equals(myUri) || myUris.contains(newUri)) {
							String message = NLS
									.bind(
											UIText.ConfigureUriPage_DuplicateUriMessage,
											newUri.toPrivateString());
							MessageDialog.openInformation(getShell(),
									UIText.ConfigureUriPage_DuplicateUriTitle,
									message);
							return;
						}
						int index = myUris.indexOf(oldUri);

						if (index > -1) {
							myUris.remove(oldUri);
							myUris.add(index, newUri);
						} else
							myUris.add(newUri);
						credentials = selectWizard.getCredentials();
						tv.setInput(myUris);
						checkPage();
					}
				}
			});

