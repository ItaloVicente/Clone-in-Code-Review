    private static List<Field> getAllDeclaredFields(final Class<?> sourceEntity) {
        List<Field> fields = new ArrayList<Field>();
        Class<?> clazz = sourceEntity;
        while (clazz != null) {
            Field[] f = clazz.getDeclaredFields();
            fields.addAll(Arrays.asList(f));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

