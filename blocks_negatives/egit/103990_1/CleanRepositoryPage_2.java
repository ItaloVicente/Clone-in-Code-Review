					try {
						final Set<String> paths = command.call();

						getShell().getDisplay().syncExec(new Runnable() {
							@Override
							public void run() {
								cleanTable.setInput(paths);
							}
						});
					} catch (GitAPIException ex) {
						Activator.logError("cannot call clean command!", ex); //$NON-NLS-1$
					}

					monitor.done();
