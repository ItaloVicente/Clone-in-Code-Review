		IStatus result = event.getJob().getResult();
		if (result.getSeverity() == IStatus.CANCEL) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					Shell shell = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell();
