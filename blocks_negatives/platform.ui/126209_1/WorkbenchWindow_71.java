				BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
					@Override
					public void run() {
						try {
							ModalContext.run(runnable, fork, manager.getProgressMonitor(),
									getShell().getDisplay());
						} catch (InvocationTargetException e) {
							ite[0] = e;
						} catch (InterruptedException e) {
							ie[0] = e;
						} finally {
							manager.getProgressMonitor().done();
						}
