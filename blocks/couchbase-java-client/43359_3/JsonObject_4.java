        Number number = (Number) content.get(name);
        if (number == null) {
            return null;
        } else if (number instanceof Integer) {
            return (Integer) number;
        } else {
            return number.intValue(); //autoboxing to Integer
        }
