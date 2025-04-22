	private void executeOpenCommand() {
		IHandlerService srv = CommonUtils.getService(getViewSite(), IHandlerService.class);

		try {
			srv.executeCommand("org.eclipse.egit.ui.RepositoriesViewOpen", null); //$NON-NLS-1$
		} catch (Exception e) {
			Activator.handleError(e.getMessage(), e, false);
		}
