					if (window.getShell() != null
							&& !window.getShell().isDisposed()) {
						window.getShell().getDisplay().syncExec(new Runnable() {
							public void run() {
								updatePinActionToolbar();
							}
						});
					}
				}
			}
		};
		WorkbenchPlugin.getDefault().getPreferenceStore()
				.addPropertyChangeListener(propPrefListener);
		resourceListener = new IResourceChangeListener() {
