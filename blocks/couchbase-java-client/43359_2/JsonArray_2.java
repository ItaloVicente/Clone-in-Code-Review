        Number n = (Number) content.get(index);
        if (n == null) {
            return null;
        } else if (n instanceof Integer) {
            return (Integer) n;
        } else {
            return n.intValue(); //autoboxing to Integer
        }
