		};
		try {
			getContainer().run(true, true, action);
		} catch (InvocationTargetException e1) {
			org.eclipse.egit.ui.Activator.handleError(
					UIText.RepositorySearchDialog_errorOccurred, e1, true);
		} catch (InterruptedException e1) {
		}
