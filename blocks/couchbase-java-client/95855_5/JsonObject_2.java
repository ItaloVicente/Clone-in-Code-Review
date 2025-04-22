    @InterfaceStability.Committed
    public Integer getAndDecryptInt(String name, String providerName) throws Exception {
        Number number = (Number) getAndDecrypt(name, providerName);
        if (number == null) {
            return null;
        } else if (number instanceof Integer) {
            return (Integer) number;
        } else {
            return number.intValue(); //autoboxing to Integer
        }
    }

