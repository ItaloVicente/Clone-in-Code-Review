    final Properties content = new Properties();
    final Map<String, AttributeView> viewsNameIndex = new HashMap<String, AttributeView>();
    final Map<Class<?>, AttributeView> viewsTypeIndex = new HashMap<Class<?>, AttributeView>();

    @Override
    public AttrsStorage getAttrStorage() {
        return this;
    }

    @Override
    public <V extends AttributeView> void addAttrView(final V view) {
        viewsNameIndex.put(view.name(),
                           view);
        if (view instanceof ExtendedAttributeView) {
            final ExtendedAttributeView extendedView = (ExtendedAttributeView) view;
            for (Class<? extends BasicFileAttributeView> type : extendedView.viewTypes()) {
                viewsTypeIndex.put(type,
                                   view);
            }
        } else {
            viewsTypeIndex.put(view.getClass(),
                               view);
        }
    }

    @Override
    public <V extends AttributeView> V getAttrView(final Class<V> type) {
        return (V) viewsTypeIndex.get(type);
    }

    @Override
    public <V extends AttributeView> V getAttrView(final String name) {
        return (V) viewsNameIndex.get(name);
    }

    @Override
    public void clear() {
        viewsNameIndex.clear();
        viewsTypeIndex.clear();
        content.clear();
    }
