		if (commit == null) {
			Activator.showError(UIText.GitHistoryPage_openFailed, null);
			return;
		}

		IWorkbenchPage activePage = site.getWorkbenchWindow().getActivePage();
