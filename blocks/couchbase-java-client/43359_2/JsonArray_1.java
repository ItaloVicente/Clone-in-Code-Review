        Number n = (Number) content.get(index);
        if (n == null) {
            return null;
        } else if (n instanceof Long) {
            return (Long) n;
        } else {
            return n.longValue(); //autoboxing to Long
        }
