        Number n = (Number) content.get(index);
        if (n == null) {
            return null;
        } else if (n instanceof Double) {
            return (Double) n;
        } else {
            return n.doubleValue(); //autoboxing to Double
        }
