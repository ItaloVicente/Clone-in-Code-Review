        Number number = (Number) content.get(name);
        if (number == null) {
            return null;
        } else if (number instanceof Double) {
            return (Double) number;
        } else {
            return number.doubleValue(); //autoboxing to Double
        }
