
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SnapshotMarkerMessage{");
        sb.append("startSequenceNumber=").append(startSequenceNumber);
        sb.append(", endSequenceNumber=").append(endSequenceNumber);
        sb.append(", memory=").append(memory);
        sb.append(", disk=").append(disk);
        sb.append(", checkpoint=").append(checkpoint);
        sb.append(", ack=").append(ack);
        sb.append('}');
        return sb.toString();
    }
