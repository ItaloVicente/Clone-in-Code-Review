    public BigInteger getBigInteger(String name) {
        return (BigInteger) content.get(name);
    }

    public BigDecimal getBigDecimal(String name) {
        Object found = content.get(name);
        if (found == null) {
            return null;
        } else if (found instanceof Double) {
            return new BigDecimal((Double) found);
        }
        return (BigDecimal) found;
    }

    public Number getNumber(String name) {
        return (Number) content.get(name);
    }

