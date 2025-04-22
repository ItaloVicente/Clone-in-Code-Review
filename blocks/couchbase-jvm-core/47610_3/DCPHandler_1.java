        extras
            .writeInt(0) // flags
            .writeInt(0) // reserved
            .writeLong(msg.startSequenceNumber()) // start sequence number
            .writeLong(msg.endSequenceNumber()) // end sequence number
            .writeLong(msg.vbucketUUID()) // vbucket UUID
            .writeLong(msg.snapshotStartSequenceNumber()) // snapshot start sequence number
            .writeLong(msg.snapshotEndSequenceNumber()); // snapshot end sequence number

