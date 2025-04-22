		super.doSetUp();
		window = openTestWindow();
		page = window.getActivePage();
		String viewId = EmptyView.ID;
		view = (EmptyView) page.showView(viewId);
		ref = page.findViewReference(viewId);
		view.addPropertyListener(propertyListener);
		titleChangeEvent = false;
		nameChangeEvent = false;
		contentChangeEvent = false;
	}

	@Override
