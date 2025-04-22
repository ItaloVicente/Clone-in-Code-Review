        Display display = createDisplay();
        DelayedEventsProcessor processor = new DelayedEventsProcessor(display);

        try {

        	Shell shell = WorkbenchPlugin.getSplashShell(display);
        	if (shell != null) {
        		shell.setText(ChooseWorkspaceDialog.getWindowTitle());
        		shell.setImages(Window.getDefaultImages());
        	}
