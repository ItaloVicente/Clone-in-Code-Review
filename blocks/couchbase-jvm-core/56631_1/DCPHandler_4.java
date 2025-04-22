                    failoverLog, rollbackToSequenceNumber, request, connection);
        } else if (msg.getOpcode() == OP_CONTROL || msg.getOpcode() == OP_BUFFER_ACK) {
            KeyValueStatus status = KeyValueStatus.valueOf(msg.getStatus());
            if (status != KeyValueStatus.SUCCESS) {
                LOGGER.warn("Unexpected status of service response (opcode={}): {} (0x{}, {})",
                        Integer.toHexString(msg.getOpcode()), status, Integer.toHexString(status.code()), status.description());
            }
