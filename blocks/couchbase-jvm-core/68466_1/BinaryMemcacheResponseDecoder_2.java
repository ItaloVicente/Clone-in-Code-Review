    private boolean isKeyValueRequest(byte opcode) {
        if (opcode >= (byte)0x00 && opcode <= (byte)0x06) {
            return true;
        }
        else if (opcode == 0x0e || opcode == 0x0f) {
            return true;
        }
        else if (opcode >= (byte)0xc5 && opcode <= (byte)0xd2) {
            return true;
        }
        return false;
    }

