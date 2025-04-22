
    private boolean shouldHaveValue(byte opcode) {
        return (opcode == OP_GET ||
                opcode == OP_GET_AND_LOCK ||
                opcode == OP_GET_REPLICA ||
                opcode == OP_GET_AND_TOUCH ||
                opcode == OP_COUNTER_INCR ||
                opcode == OP_COUNTER_DECR ||
                opcode == OP_SUB_COUNTER ||
                opcode == OP_SUB_GET);

    }

    private boolean shouldHaveExtras(byte opcode) {
        return (opcode == OP_GET ||
                opcode == OP_GET_AND_LOCK ||
                opcode == OP_GET_REPLICA ||
                opcode == OP_GET_AND_TOUCH);
    }

    private boolean shouldHaveCAS(byte opcode) {
        return  (opcode == OP_GET ||
                opcode == OP_GET_AND_LOCK ||
                opcode == OP_GET_REPLICA ||
                opcode == OP_GET_AND_TOUCH ||
                opcode == OP_COUNTER_INCR ||
                opcode == OP_COUNTER_DECR ||
                opcode == OP_UPSERT ||
                opcode == OP_REPLACE ||
                opcode == OP_INSERT ||
                opcode == OP_APPEND ||
                opcode == OP_PREPEND ||
                ((opcode >= OP_SUB_GET) && (opcode <= OP_SUB_COUNTER)));
    }

