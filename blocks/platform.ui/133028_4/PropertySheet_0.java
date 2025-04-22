		MessagePage page = new MessagePage();
		initPage(page);
		page.createControl(book);
		page.setMessage(defaultText);
		return page;
	}

	protected IPage createFallbackPage(PageBook book) {
		IPropertySheetPage page = Platform.getAdapterManager().getAdapter(this, IPropertySheetPage.class);
		if (page == null) {
			page = new PropertySheetPage();
		}
		if (page instanceof IPageBookViewPage) {
			initPage((IPageBookViewPage) page);
		}
		page.createControl(book);
		return page;
	}
