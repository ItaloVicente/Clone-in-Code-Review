        if (adapter.equals(IContentOutlinePage.class)) {
            return (T) getContentOutline();
        }
        if (adapter.equals(IPropertySheetPage.class)) {
            return (T) getPropertySheet();
        }
        return super.getAdapter(adapter);
    }
