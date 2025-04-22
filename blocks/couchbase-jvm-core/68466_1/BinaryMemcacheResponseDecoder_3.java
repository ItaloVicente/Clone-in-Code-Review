
        byte opcode = header.getOpcode();
        byte magic = header.getMagic();
        short keyLength = header.getKeyLength();
        byte extrasLength = header.getExtrasLength();
        short status = header.getStatus();
        int bodyLength = header.getTotalBodyLength();
        long cas = header.getCAS();
        int maxBodyLength = MAX_VALUE_LENGTH + keyLength + extrasLength;

        if (status < 0) {
            throw new MalformedMemcacheHeaderException("Invalid status, received " + status);
        }

        if (status == (short)0x0) {
            if (magic != DefaultBinaryMemcacheResponse.RESPONSE_MAGIC_BYTE) {
                throw new MalformedMemcacheHeaderException("Invalid response magic byte, received " + magic + " bytes");
            }

            if (isKeyValueRequest(opcode) && (keyLength) <= 0 || keyLength > MAX_KEY_LENGTH) {
                throw new MalformedMemcacheHeaderException("Invalid key length, received " + header.getKeyLength() + " bytes");
            }

            if (isKeyValueRequest(opcode) && extrasLength <= 0) {
                throw new MalformedMemcacheHeaderException("Invalid extras length, received " + extrasLength + "bytes");
            }

            if (isKeyValueRequest(opcode) && (bodyLength <= 0 || bodyLength > maxBodyLength)) {
                throw new MalformedMemcacheHeaderException("Invalid response data length, received " + bodyLength + " bytes");
            }

            if (isKeyValueRequest(opcode) && cas <= 0) {
                throw new MalformedMemcacheHeaderException("Invalid CAS value, received " + cas);
            }
        }
