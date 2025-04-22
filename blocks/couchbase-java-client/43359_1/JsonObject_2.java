        Number number = (Number) content.get(name);
        if (number == null) {
            return null;
        } else if (number instanceof Long) {
            return (Long) number;
        } else {
            return number.longValue(); //autoboxing to Long
        }
