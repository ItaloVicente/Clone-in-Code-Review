	public void windowActivated(IWorkbenchWindow window) {
	}

	public void windowDeactivated(IWorkbenchWindow window) {
	}

	public void windowClosed(IWorkbenchWindow window) {
		buildActions.remove(window);
	}

	public void windowOpened(IWorkbenchWindow window) {
	} 		  

