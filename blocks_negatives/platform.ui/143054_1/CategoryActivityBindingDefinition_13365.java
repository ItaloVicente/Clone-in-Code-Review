        while (iterator.hasNext()) {
            Object object = iterator.next();
            Util
                    .assertInstance(object,
                            CategoryActivityBindingDefinition.class);
            CategoryActivityBindingDefinition categoryActivityBindingDefinition = (CategoryActivityBindingDefinition) object;
            String categoryId = categoryActivityBindingDefinition
                    .getCategoryId();
