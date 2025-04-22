            List<FailoverLogEntry> failoverLog = null;
            long rollbackToSequenceNumber = 0;
            KeyValueStatus status = KeyValueStatus.valueOf(msg.getStatus());
            switch (status) {
                case SUCCESS:
                    failoverLog = new ArrayList<FailoverLogEntry>(content.readableBytes() / 16);
                    while (content.readableBytes() >= 16) {
                        FailoverLogEntry entry = new FailoverLogEntry(content.readLong(), content.readLong());
                        failoverLog.add(entry);
                    }
                    break;
                case ERR_ROLLBACK:
                    rollbackToSequenceNumber = content.readLong();
                    break;
                default:
                    LOGGER.warn("Unexpected status of StreamRequestResponse: {} (0x{}, {})",
                            status, Integer.toHexString(status.code()), status.description());

