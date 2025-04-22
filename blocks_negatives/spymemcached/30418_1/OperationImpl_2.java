      int toRead = payload.length - payloadOffset;
      int available = b.remaining();
      toRead = Math.min(toRead, available);
      getLogger().debug("Reading %d payload bytes", toRead);
      b.get(payload, payloadOffset, toRead);
      payloadOffset += toRead;

      if (payloadOffset == payload.length) {
        finishedPayload(payload);
      }
