				if (handler != null) {
					Shell splashShell = handler.getSplash();
					if (splashShell != null && !splashShell.isDisposed()) {
						splashShell.setVisible(true);
						splashShell.forceActive();
					}
				}
				spinEventQueueToUpdateSplash(display);
