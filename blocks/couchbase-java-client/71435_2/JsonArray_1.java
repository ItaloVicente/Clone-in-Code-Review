    public BigInteger getBigInteger(int index) {
        return (BigInteger) content.get(index);
    }

    public BigDecimal getBigDecimal(int index) {
        Object found = content.get(index);
        if (found == null) {
            return null;
        } else if (found instanceof Double) {
            return new BigDecimal((Double) found);
        }
        return (BigDecimal) found;
    }

    public Number getNumber(int index) {
        return (Number) content.get(index);
    }

