						PlatformUI.getWorkbench().getDisplay().asyncExec(
								() -> {
									if (!isOpen()
											&& BundleSigningInfo.this.data != myData)
										return;
									certificate.setText(signerText);
									date.setText(dateText);
								});
