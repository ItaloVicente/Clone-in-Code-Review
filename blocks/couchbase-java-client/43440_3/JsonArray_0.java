    public static JsonArray from(List<?> items) {
        if (items == null) {
            throw new NullPointerException("Null list unsupported");
        } else if (items.isEmpty()) {
            return JsonArray.empty();
        }

        JsonArray array = new JsonArray();
        ListIterator<?> iter = items.listIterator();
        while (iter.hasNext()) {
            int i = iter.nextIndex();
            Object item = iter.next();

            if (item instanceof Map) {
                try {
                    JsonObject sub = JsonObject.from((Map<String, ?>) item);
                    array.add(sub);
                } catch (ClassCastException e) {
                    throw e;
                } catch (Exception e) {
                    ClassCastException c = new ClassCastException("Couldn't convert sub-Map at " + i + " to JsonObject");
                    c.initCause(e);
                    throw c;
                }
            } else if (item instanceof List) {
                try {
                    JsonArray sub = JsonArray.from((List<?>) item);
                    array.add(sub);
                } catch (Exception e) {
                    ClassCastException c = new ClassCastException(
                            "Couldn't convert sub-List at " + i + " to JsonArray");
                    c.initCause(e);
                    throw c;
                }
            } else if (checkType(item)) {
                array.add(item);
            } else {
                throw new IllegalArgumentException("Unsupported type for JsonArray: " + item.getClass());
            }
        }
        return array;
    }

