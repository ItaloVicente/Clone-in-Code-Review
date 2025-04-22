    int bufSize = 0;
    bufSize += HEADER_LENGTH;
    if (opcode.equals(TapOpcode.MUTATION)) {
      bufSize += 16;
    }
    bufSize += getTotalbody();

    ByteBuffer bb = ByteBuffer.allocate(bufSize);
