        if (datatypes.json() && request.isJson()) {
            if (compress) {
                msg.setDataType((byte) 0x03);
            } else {
                msg.setDataType((byte) 0x01);
            }
        } else if (compress) {
            msg.setDataType((byte) 0x02);
        }
