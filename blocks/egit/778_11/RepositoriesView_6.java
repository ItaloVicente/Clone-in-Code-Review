	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySheetPage.class) {
			PropertySheetPage page = new PropertySheetPage();
			page.setPropertySourceProvider(new RepositoryPropertySourceProvider(
					page));
			return page;
		}
		return super.getAdapter(adapter);
	}
