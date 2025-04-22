		MessagePage page = new MessagePage();
		initPage(page);
		page.createControl(book);
		page.setMessage(defaultText);
		return page;
	}

	protected IPage createFallbackPage(PageBook book) {
		IPageBookViewPage page = new PropertySheetPage();
		initPage(page);
		page.createControl(book);
		return page;
	}
