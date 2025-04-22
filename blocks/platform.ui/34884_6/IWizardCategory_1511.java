package org.eclipse.ui.views;


public interface IViewRegistry {
    public IViewDescriptor find(String id);

    public IViewCategory[] getCategories();

    public IViewDescriptor[] getViews();

    public IStickyViewDescriptor[] getStickyViews();
}
