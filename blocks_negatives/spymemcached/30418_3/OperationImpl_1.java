        int magic = header[0];
        assert magic == RES_MAGIC : "Invalid magic:  " + magic;
        responseCmd = header[1];
        assert cmd == DUMMY_OPCODE || responseCmd == cmd
          : "Unexpected response command value";
        keyLen = decodeShort(header, 2);
        errorCode = decodeShort(header, 6);
        int bytesToRead = decodeInt(header, 8);
        payload = new byte[bytesToRead];
        responseOpaque = decodeInt(header, 12);
        responseCas = decodeLong(header, 16);
        assert opaqueIsValid() : "Opaque is not valid";
