        if (adapter == IPropertySheetPage.class) {
            if (tabbedPropertySheetPage == null) {
                tabbedPropertySheetPage = new TabbedPropertySheetPageWithDecorations(this);
            }
            return tabbedPropertySheetPage;
        }
        return super.getAdapter(adapter);
    }
