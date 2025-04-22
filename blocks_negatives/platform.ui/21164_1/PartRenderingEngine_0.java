			/**
			 * Starts the final model assembly phase (bug 376486)
			 */
			private void modelAssemblerFinalStep() {
					protected IStatus run(IProgressMonitor monitor) {
						monitor.beginTask("Running Model Assembler",
								IProgressMonitor.UNKNOWN);

						while (getJobManager().find("earlyStartup").length > 0) {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
							}
							if (monitor.isCanceled()) {
								break;
							}
						}

						if (!monitor.isCanceled()) {
							String xmiUriArg = System
									.getProperty(org.eclipse.e4.ui.workbench.IWorkbench.XMI_URI_ARG);
								ModelAssembler contribProcessor = ContextInjectionFactory
										.make(ModelAssembler.class, appContext);
								contribProcessor
										.processModel(ModelAssembler.E3_E3STEP);
							}
							monitor.done();
							return Status.OK_STATUS;
						}
						return Status.CANCEL_STATUS;
					}
				};
				job.setSystem(true);
				job.schedule();
			}

