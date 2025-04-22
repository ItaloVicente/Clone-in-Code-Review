						PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
							if (!isOpen() && BundleSigningInfo.this.data != myData)
								return;
							certificate.setText(signerText);
							date.setText(dateText);
						});

					} catch (IOException | GeneralSecurityException e2) {
						return new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH, e2.getMessage(), e2);
