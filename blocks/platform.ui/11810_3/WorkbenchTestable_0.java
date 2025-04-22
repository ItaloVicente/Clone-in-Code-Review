					if ("true".equalsIgnoreCase(System.getProperty(PlatformUI.PLUGIN_ID + ".testsDisableWorkbenchAutoSave"))) { //$NON-NLS-1$ //$NON-NLS-2$
						if (WorkbenchTestable.this.workbench instanceof Workbench) {
							((Workbench) WorkbenchTestable.this.workbench).setEnableAutoSave(false);
						}
						Job.getJobManager().cancel(Workbench.WORKBENCH_AUTO_SAVE_JOB);
					}
