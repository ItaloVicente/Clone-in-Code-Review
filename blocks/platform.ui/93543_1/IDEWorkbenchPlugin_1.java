		} else {
			Job job = new Job("Initializing Problems view") { //$NON-NLS-1$
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					IWorkbench workbench = PlatformUI.getWorkbench();
					if (workbench == null) {
						schedule(PROBLEMS_VIEW_CREATION_DELAY);
						return Status.OK_STATUS;
					}
					if (workbench != null && workbench.isClosing()) {
						return Status.CANCEL_STATUS;
					}
					PlatformUI.getWorkbench().getDisplay().asyncExec(r);
					return Status.OK_STATUS;
				}
			};
			job.setSystem(true);
			job.setUser(false);
			job.schedule(PROBLEMS_VIEW_CREATION_DELAY);
		}
