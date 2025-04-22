  @Override
  public final void handleLine(String line) {
    if (line.equals("END")) {
      getLogger().debug("Get complete!");
      if (hasValue) {
        getCallback().receivedStatus(END);
      } else {
        getCallback().receivedStatus(NOT_FOUND);
      }
      transitionState(OperationState.COMPLETE);
      data = null;
    } else if (line.startsWith("VALUE ")) {
      getLogger().debug("Got line %s", line);
      String[] stuff = line.split(" ");
      assert stuff[0].equals("VALUE");
      currentKey = stuff[1];
      currentFlags = Integer.parseInt(stuff[2]);
      data = new byte[Integer.parseInt(stuff[3])];
      if (stuff.length > 4) {
        casValue = Long.parseLong(stuff[4]);
      }
      readOffset = 0;
      hasValue = true;
      getLogger().debug("Set read type to data");
      setReadType(OperationReadType.DATA);
    } else {
      assert false : "Unknown line type: " + line;
    }
  }
