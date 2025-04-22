    public static JsonArray from(List<?> items) {
        if (items == null || items.isEmpty()) {
            return JsonArray.empty();
        }

        JsonArray array = new JsonArray();
        ListIterator<?> iter = items.listIterator();
        while (iter.hasNext()) {
            int index = iter.nextIndex();
            Object item = iter.next();
            if (item == null) {
                throw new NullPointerException("Unsupported null item at index " + index);
            } else if (checkType(item)) {
                array.add(item);
            } else {
                throw new IllegalArgumentException("Unsupported type for JsonArray: " + item.getClass());
            }
        }
        return array;
    }

