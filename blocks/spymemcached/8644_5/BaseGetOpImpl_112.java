    getLogger().debug("readOffset: %d, length: %d", readOffset, data.length);
    if (lookingFor == '\0') {
      int toRead = data.length - readOffset;
      int available = b.remaining();
      toRead = Math.min(toRead, available);
      getLogger().debug("Reading %d bytes", toRead);
      b.get(data, readOffset, toRead);
      readOffset += toRead;
    }
    if (readOffset == data.length && lookingFor == '\0') {
      OperationCallback cb = getCallback();
      if (cb instanceof GetOperation.Callback) {
        GetOperation.Callback gcb = (GetOperation.Callback) cb;
        gcb.gotData(currentKey, currentFlags, data);
      } else if (cb instanceof GetsOperation.Callback) {
        GetsOperation.Callback gcb = (GetsOperation.Callback) cb;
        gcb.gotData(currentKey, currentFlags, casValue, data);
      } else if (cb instanceof GetlOperation.Callback) {
        GetlOperation.Callback gcb = (GetlOperation.Callback) cb;
        gcb.gotData(currentKey, currentFlags, casValue, data);
      } else if (cb instanceof GetAndTouchOperation.Callback) {
        GetAndTouchOperation.Callback gcb = (GetAndTouchOperation.Callback) cb;
        gcb.gotData(currentKey, currentFlags, casValue, data);
      } else {
        throw new ClassCastException("Couldn't convert " + cb
            + "to a relevent op");
      }
      lookingFor = '\r';
    }
    if (lookingFor != '\0' && b.hasRemaining()) {
      do {
        byte tmp = b.get();
        assert tmp == lookingFor : "Expecting " + lookingFor + ", got "
            + (char) tmp;
        switch (lookingFor) {
        case '\r':
          lookingFor = '\n';
          break;
        case '\n':
          lookingFor = '\0';
          break;
        default:
          assert false : "Looking for unexpected char: " + (char) lookingFor;
        }
      } while (lookingFor != '\0' && b.hasRemaining());
      if (lookingFor == '\0') {
        currentKey = null;
        data = null;
        readOffset = 0;
        currentFlags = 0;
        getLogger().debug("Setting read type back to line.");
        setReadType(OperationReadType.LINE);
      }
    }
  }
