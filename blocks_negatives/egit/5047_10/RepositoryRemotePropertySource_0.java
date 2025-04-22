		IToolBarManager mgr = page.getSite().getActionBars()
				.getToolBarManager();
		boolean update = false;
		update = update
				| mgr.remove(RepositoryPropertySource.CHANGEMODEACTIONID) != null;
		update = update
				| mgr.remove(RepositoryPropertySource.SINGLEVALUEACTIONID) != null;
		update = update
				| mgr.remove(RepositoryPropertySource.EDITACTIONID) != null;
		if (update)
			mgr.update(false);
