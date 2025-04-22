
    	if(Display.getCurrent() != null) {
			return new ImageRegistry(Display.getCurrent());
		}

    	if(PlatformUI.isWorkbenchRunning()) {
			return new ImageRegistry(PlatformUI.getWorkbench().getDisplay());
