		MessagePage page = new MessagePage();
		initPage(page);
		page.createControl(book);
		page.setMessage(defaultText);
		return page;
	}

	protected IPage createFallbackPage(PageBook book) {
		IPageBookViewPage page = Platform.getAdapterManager().getAdapter(this, PropertySheetPage.class);
		if (page == null) {
			page = new PropertySheetPage();
		}
		initPage(page);
		page.createControl(book);
		return page;
	}
