						Runnable runnable = () -> ((ILazyContentProvider) contentProvider).updateElement(index);
						if (isBusy()) {
							getControl().getDisplay().asyncExec(runnable);
						} else {
							runnable.run();
						}
