		try {
			GitProjectData.reconfigureWindowCache();
			return true;
		} catch (RuntimeException e) {
			org.eclipse.egit.ui.Activator.handleError(e.getMessage(), e, true);
			return false;
		}
