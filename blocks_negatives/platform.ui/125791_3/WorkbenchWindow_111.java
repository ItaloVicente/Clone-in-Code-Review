				BusyIndicator.showWhile(getShell().getDisplay(), () -> {
					try {
						ModalContext.run(runnable, fork, manager.getProgressMonitor(), getShell().getDisplay());
					} catch (InvocationTargetException e1) {
						ite[0] = e1;
					} catch (InterruptedException e2) {
						ie[0] = e2;
					} finally {
						manager.getProgressMonitor().done();
