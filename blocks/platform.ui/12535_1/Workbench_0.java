							try {
								persist(false);
								monitor.done();
							} finally {
								if (nextDelay > 0 && workbenchAutoSave) {
									this.schedule(nextDelay);
								}
