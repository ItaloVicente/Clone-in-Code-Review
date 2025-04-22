		IPageBookViewPage page = (IPageBookViewPage) Adapters.adapt(this, IPropertySheetPage.class);
        if(page == null) {
        	page = new PropertySheetPage();
        }
        initPage(page);
        page.createControl(book);
        return page;
    }
