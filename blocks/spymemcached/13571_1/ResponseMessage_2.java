    } else if (opcode.equals(TapOpcode.START_CHECKPOINT) || opcode.equals(TapOpcode.END_CHECKPOINT)) {
        itemflags = 0;
        itemexpiry = 0;
        vbucketstate = (int)decodeLong(b, KEY_OFFSET);
        key = new byte[0];
        value = new byte[0];
        revid = new byte[0];
    } else if(opcode.equals(TapOpcode.OPAQUE)) {
        itemflags = 0;
        itemexpiry = 0;
        vbucketstate = (int)decodeInt(b, ITEM_FLAGS_OFFSET);
        key = new byte[0];
        value = new byte[0];
        revid = new byte[0];
