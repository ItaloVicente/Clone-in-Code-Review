    public BigDecimal getAndDecryptBigDecimal(String name, String providerName) throws Exception {
        Object found = getAndDecrypt(name, providerName);
        if (found == null) {
            return null;
        } else if (found instanceof Double) {
            return new BigDecimal((Double) found);
        }
        return (BigDecimal) found;
    }

