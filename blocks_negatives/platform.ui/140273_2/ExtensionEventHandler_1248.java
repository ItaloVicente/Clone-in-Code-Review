		    if (MessageDialog
		            .openQuestion(
		                    parentShell,
		                    ExtensionEventHandlerMessages.ExtensionEventHandler_reset_perspective, message.toString())) {
		        IWorkbenchPage page = window.getActivePage();
		        if (page == null) {
