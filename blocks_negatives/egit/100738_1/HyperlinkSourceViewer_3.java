			hyperlinkChangeListener = new IPropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					String property = event.getProperty();
					if (preferenceKeysForEnablement.contains(property)) {
						resetHyperlinkDetectors();
						final Control control = getControl();
						if (control != null && !control.isDisposed()) {
							control.getDisplay().asyncExec(new Runnable() {
								@Override
								public void run() {
									if (!control.isDisposed()) {
										refresh();
									}
								}
							});
						}
					} else if (preferenceKeysForActivation.contains(property)) {
						resetHyperlinkDetectors();
					}
