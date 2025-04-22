			private void modelAssemblerFinalStep() {
				final Job job = new Job("Workbench Final Model Assembly") { //$NON-NLS-1$
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
							if ("org.eclipse.ui.workbench/LegacyIDE.e4xmi".equals(xmiUriArg)) { //$NON-NLS-1$
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

