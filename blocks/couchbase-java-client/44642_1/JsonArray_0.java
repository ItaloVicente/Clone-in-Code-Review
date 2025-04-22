        List<Object> copy = new ArrayList<Object>(content.size());
        for (Object o : content) {
            if (o instanceof JsonObject) {
                copy.add(((JsonObject) o).toMap());
            } else if (o instanceof JsonArray) {
                copy.add(((JsonArray) o).toList());
            } else {
                copy.add(o);
            }
        }
        return copy;
