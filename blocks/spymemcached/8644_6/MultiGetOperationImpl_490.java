      bb.put(REQ_MAGIC);
      bb.put((byte) CMD_GETQ);
      bb.putShort((short) keyBytes.length);
      bb.put((byte) 0); // extralen
      bb.put((byte) 0); // data type
      bb.putShort(vbmap.get(key).shortValue()); // vbucket
      bb.putInt(keyBytes.length);
      bb.putInt(me.getKey());
      bb.putLong(0); // cas
      bb.put(keyBytes);
    }
    bb.put(REQ_MAGIC);
    bb.put((byte) NoopOperationImpl.CMD);
    bb.putShort((short) 0);
    bb.put((byte) 0); // extralen
    bb.put((byte) 0); // data type
    bb.putShort((short) 0); // reserved
    bb.putInt(0);
    bb.putInt(terminalOpaque);
    bb.putLong(0); // cas
