	protected IPage createPropertySheetPage(PageBook book) {
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

