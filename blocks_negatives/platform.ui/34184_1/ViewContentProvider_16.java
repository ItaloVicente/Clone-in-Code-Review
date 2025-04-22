    private Object[] createChildren(Object element) {
        if (element instanceof IViewRegistry) {
            IViewRegistry reg = (IViewRegistry) element;
            IViewCategory [] categories = reg.getCategories();

			ArrayList<IViewCategory> filtered = new ArrayList<IViewCategory>();
            for (int i = 0; i < categories.length; i++) {
                if (!hasChildren(categories[i])) {
