				mgr.setCancelEnabled(cancelable);
				final Exception[] holder = new Exception[1];
				BusyIndicator.showWhile(display, () -> {
					try {
						ModalContext.run(runnable, fork, mgr
								.getProgressMonitor(), display);
					} catch (InvocationTargetException ite) {
						holder[0] = ite;
					} catch (InterruptedException ie) {
						holder[0] = ie;
					}
