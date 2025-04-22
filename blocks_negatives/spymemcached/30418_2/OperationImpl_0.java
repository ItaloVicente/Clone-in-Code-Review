      int toRead = MIN_RECV_PACKET - headerOffset;
      int available = b.remaining();
      toRead = Math.min(toRead, available);
      getLogger().debug("Reading %d header bytes", toRead);
      b.get(header, headerOffset, toRead);
      headerOffset += toRead;

