    private BinaryMemcacheRequest handleReplicaGetRequest(final ReplicaGetRequest request) {
        int length = request.key().length();
        BinaryMemcacheRequest msg = new DefaultBinaryMemcacheRequest(request.key());
        msg.setOpcode((byte) 0x83);
        msg.setKeyLength((short) length);
        msg.setTotalBodyLength((short) length);
        msg.setReserved(request.partition());
        return msg;
    }


