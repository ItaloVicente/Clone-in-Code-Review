
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							if (!isOpen() && BundleSigningInfo.this.data != myData)
								return;
							certificate.setText(signerText);
							date.setText(dateText);

						}
					});
