		if (PlatformUI.isWorkbenchRunning()) {
			UIJob registerJob = new UIJob(Display.getDefault(),
					"Git Clone DND Initialization") { //$NON-NLS-1$
				{
					setPriority(Job.SHORT);
					setSystem(true);
				}
