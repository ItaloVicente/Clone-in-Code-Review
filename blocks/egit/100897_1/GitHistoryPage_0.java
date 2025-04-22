					Control control = historyPage.getControl();
					if (control != null && !control.isDisposed()) {
						control.getDisplay().asyncExec(() -> {
							if (!control.isDisposed()) {
								setChecked(
										historyPage.store.getBoolean(prefName));
								apply(isChecked());
							}
						});
					}
