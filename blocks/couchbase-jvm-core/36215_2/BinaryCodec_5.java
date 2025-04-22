    private BinaryMemcacheRequest handleRemoveRequest(final RemoveRequest request) {
        BinaryMemcacheRequest msg = new DefaultBinaryMemcacheRequest(request.key());

        msg.setOpcode(BinaryMemcacheOpcodes.DELETE);
        msg.setCAS(request.cas());
        msg.setKeyLength((short) request.key().length());
        msg.setTotalBodyLength((short) request.key().length());
        msg.setReserved(request.partition());
        return msg;
    }

