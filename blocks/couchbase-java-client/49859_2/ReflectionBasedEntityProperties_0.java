
    @Override
    public String actualFieldPropertyName(Field field) {
        com.couchbase.client.java.repository.mapping.annotation.Field annotation =
            field.getDeclaredAnnotation(com.couchbase.client.java.repository.mapping.annotation.Field.class);

        if (annotation == null) {
            return field.getName();
        } else {
            String alias = annotation.value();
            return alias != null && !alias.isEmpty() ? alias : field.getName();
        }
    }

