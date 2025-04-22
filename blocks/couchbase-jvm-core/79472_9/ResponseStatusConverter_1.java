    public static ResponseStatusDetails detailsFromBinary(final byte dataType, final ByteBuf content) {
        if ((dataType & JSON_DATATYPE) != 0) {
            return ResponseStatusDetails.convert(content.slice());
        } else {
            return null;
        }
    }

