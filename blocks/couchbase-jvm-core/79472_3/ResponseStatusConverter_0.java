    public static ResponseStatusDetails detailsFromBinary(byte dataType, ByteBuf content) {
        if (dataType == JSON_DATAYPE) {
            return ResponseStatusDetails.convert(content.slice());
        } else {
            return null;
        }
    }

