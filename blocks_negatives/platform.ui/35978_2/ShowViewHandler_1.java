		
		final IViewDescriptor[] descriptors = dialog.getSelection();
		for (int i = 0; i < descriptors.length; ++i) {
			try {
                openView(descriptors[i].getId(), window);
			} catch (PartInitException e) {
				StatusUtil.handleStatus(e.getStatus(),
						WorkbenchMessages.ShowView_errorTitle
								+ ": " + e.getMessage(), //$NON-NLS-1$
						StatusManager.SHOW);
			}
