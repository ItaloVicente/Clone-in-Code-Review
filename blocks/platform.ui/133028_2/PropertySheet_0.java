		MessagePage page = new MessagePage();
		initPage(page);
		page.createControl(book);
		page.setMessage(defaultText);
		return page;
	}

	protected IPage createFallbackPage(PageBook book) {
