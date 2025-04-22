					popup.getShell().addDisposeListener(new DisposeListener() {
						@Override
						public void widgetDisposed(DisposeEvent event) {
							popup = null;
						}
					});
