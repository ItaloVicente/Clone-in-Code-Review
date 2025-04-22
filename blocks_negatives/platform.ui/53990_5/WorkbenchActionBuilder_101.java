        propPrefListener = new IPropertyChangeListener() {
            @Override
			public void propertyChange(PropertyChangeEvent event) {
                if (event.getProperty().equals(
						IPreferenceConstants.REUSE_EDITORS_BOOLEAN)) {
                    if (window.getShell() != null
                            && !window.getShell().isDisposed()) {
						window.getShell().getDisplay().asyncExec(new Runnable() {
                            @Override
							public void run() {
								if (window.getShell() != null && !window.getShell().isDisposed()) {
									updatePinActionToolbar();
								}
                            }
                        });
                    }
                }
            }
        };
