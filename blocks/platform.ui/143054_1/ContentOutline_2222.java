		MessagePage page = new MessagePage();
		initPage(page);
		page.createControl(book);
		page.setMessage(defaultText);
		return page;
	}

	@Override
