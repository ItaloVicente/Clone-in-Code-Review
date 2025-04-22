  private void readHeaderFromBuffer(final ByteBuffer buffer) {
    int toRead = MIN_RECV_PACKET - headerOffset;
    int available = buffer.remaining();
    toRead = Math.min(toRead, available);
    getLogger().debug("Reading %d header bytes", toRead);
    buffer.get(header, headerOffset, toRead);
    headerOffset += toRead;
  }

  private void parseHeaderFrombuffer() {
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
  }

  private void readPayloadfromBuffer(final ByteBuffer buffer)
    throws IOException {
    int toRead = payload.length - payloadOffset;
    int available = buffer.remaining();
    toRead = Math.min(toRead, available);
    getLogger().debug("Reading %d payload bytes", toRead);
    buffer.get(payload, payloadOffset, toRead);
    payloadOffset += toRead;

    if (payloadOffset == payload.length) {
      finishedPayload(payload);
    }
  }

