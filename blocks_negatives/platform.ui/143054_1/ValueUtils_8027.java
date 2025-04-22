        try {
            return TypeUtils.convert(value, type);
        }
        catch (Exception ex) {
            throw new JXPathException(
                "Cannot convert value of class "
                    + (value == null ? "null" : value.getClass().getName())
                    + " to type "
                    + type,
                ex);
        }
    }
