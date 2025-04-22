        display.asyncExec(new Runnable() {
            @Override
			public void run() {
                Shell parentShell = null;
                IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
                if (window == null) {
                    if (workbench.getWorkbenchWindowCount() == 0) {
						return;
					}
                    window = workbench.getWorkbenchWindows()[0];
                }

                parentShell = window.getShell();

                if (MessageDialog
                        .openQuestion(
                                parentShell,
                                ExtensionEventHandlerMessages.ExtensionEventHandler_reset_perspective, message.toString())) {
                    IWorkbenchPage page = window.getActivePage();
                    if (page == null) {
						return;
					}
                    page.resetPerspective();
                }
            }
        });
