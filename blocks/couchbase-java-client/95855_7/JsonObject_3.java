    public Long getAndDecryptLong(String name, String providerName) throws Exception {
        Number number = (Number) getAndDecrypt(name, providerName);
        if (number == null) {
            return null;
        } else if (number instanceof Long) {
            return (Long) number;
        } else {
            return number.longValue(); //autoboxing to Long
        }
    }

