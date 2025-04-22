				try {
					setOpenOnRun(true);
					ProgressMonitorFocusJobDialog.this.run(true, true, getRunnableForJob(job));
				} catch (InterruptedException ie) {
					return new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH, ie.getLocalizedMessage(), ie);
				} catch (InvocationTargetException ite) {
					return new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH, ite.getLocalizedMessage(), ite);
				}
