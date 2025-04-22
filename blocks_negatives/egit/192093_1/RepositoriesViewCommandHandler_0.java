			RepositoriesView view = null;
			IWorkbenchPart part = HandlerUtil.getActivePartChecked(event);
			if (part instanceof RepositoriesView) {
				view = ((RepositoriesView) part);
			} else {
				view = getRepositoriesView();
			}
