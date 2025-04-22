						ILazyContentProvider lazyProvider = (ILazyContentProvider) contentProvider;
						if (!isBusy()) {
							lazyProvider.updateElement(index);
						} else {
							getControl().getDisplay().asyncExec(() -> lazyProvider.updateElement(index));
						}
