    private Map<Object, Object[]> childMap = new HashMap<Object, Object[]>();

    /**
     * Create a new instance of the ViewContentProvider.
     */
    public ViewContentProvider() {
    }

    @Override
	public void dispose() {
        childMap.clear();
    }

    @Override
	public Object[] getChildren(Object element) {
        Object[] children = childMap.get(element);
        if (children == null) {
            children = createChildren(element);
            childMap.put(element, children);
        }
        return children;
    }

    /**
	 * Does the actual work of getChildren.
	 */
    private Object[] createChildren(Object element) {
        if (element instanceof IViewRegistry) {
            IViewRegistry reg = (IViewRegistry) element;
            IViewCategory [] categories = reg.getCategories();
