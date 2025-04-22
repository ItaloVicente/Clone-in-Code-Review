    private BinaryMemcacheRequest handleFailoverLogsRequest(ChannelHandlerContext ctx, GetFailoverLogRequest msg) {
        BinaryMemcacheRequest request = new DefaultBinaryMemcacheRequest();
        request.setOpcode(OP_GET_FAILOVER_LOG);
        request.setReserved(msg.partition());

        return request;
    }

    private List<FailoverLogEntry> readFailoverLogs(final ByteBuf content) {
        List<FailoverLogEntry> failoverLog = new ArrayList<FailoverLogEntry>(content.readableBytes() / 16);
        while (content.readableBytes() >= 16) {
            FailoverLogEntry entry = new FailoverLogEntry(content.readLong(), content.readLong());
            failoverLog.add(entry);
        }
        return failoverLog;
    }

