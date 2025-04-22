		Job job = new Job(UIText.Activator_setupFocusListener) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				if (PlatformUI.isWorkbenchRunning())
					PlatformUI.getWorkbench().addWindowListener(focusListener);
				else
					schedule(1000L);
				return Status.OK_STATUS;
			}
		};
		job.schedule();
