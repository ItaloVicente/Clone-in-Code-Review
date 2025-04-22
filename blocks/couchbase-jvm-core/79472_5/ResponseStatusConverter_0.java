    public static ResponseStatusDetails detailsFromBinary(final byte dataType, final ByteBuf content) {
        if (dataType == JSON_DATATYPE) {
            return ResponseStatusDetails.convert(content.slice());
        } else {
            return null;
        }
    }

