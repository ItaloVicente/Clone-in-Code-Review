    public Double getAndDecryptDouble(String name, String providerName) throws Exception {
        Number number = (Number) getAndDecrypt(name, providerName);
        if (number == null) {
            return null;
        } else if (number instanceof Double) {
            return (Double) number;
        } else {
            return number.doubleValue(); //autoboxing to Double
        }
    }

