		if (adapter == IPropertySheetPage.class) {
			if (tabbedPropertySheetPage == null) {
				tabbedPropertySheetPage = new TabbedPropertySheetPage(this);
			}
			return tabbedPropertySheetPage;
		}
		return super.getAdapter(adapter);
	}
