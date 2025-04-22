        extras.writeInt(0).writeInt(0);
        extras.writeLong(msg.startSequenceNumber());
        extras.writeLong(msg.endSequenceNumber());
        extras.writeLong(msg.vbucketUUID());
        extras.writeLong(msg.snapshotStartSequenceNumber());
        extras.writeLong(msg.snapshotEndSequenceNumber());
