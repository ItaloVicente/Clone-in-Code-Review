        collection = getValue(collection);
        if (collection != null) {
            if (collection.getClass().isArray()) {
                Array.set(
                    collection,
                    index,
                    convert(value, collection.getClass().getComponentType()));
            }
            else if (collection instanceof List) {
                ((List<Object>) collection).set(index, value);
            }
            else if (collection instanceof Collection) {
                throw new UnsupportedOperationException(
                        "Cannot set value of an element of a "
                                + collection.getClass().getName());
            }
        }
    }
