        String sourceId = sourceIdOverride != null ? sourceIdOverride : memento
                .getString(TAG_SOURCE_ID);
        return new CategoryActivityBindingDefinition(activityId, categoryId,
                sourceId);
    }

    static CategoryDefinition readCategoryDefinition(IMemento memento,
            String sourceIdOverride) {

        String id = memento.getString(TAG_ID);
        if (id == null) {
        	log(memento, CATEGORY_DESC, "has no ID"); //$NON-NLS-1$
