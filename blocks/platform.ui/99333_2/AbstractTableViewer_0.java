							Control control = getControl();
							control.getDisplay().asyncExec(() -> {
								if (!control.isDisposed()) {
									lazyProvider.updateElement(index);
								}
							});
