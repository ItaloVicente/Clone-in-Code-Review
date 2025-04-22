		final int crc32 = (int) crc.getValue();
		addObjectAndTrack(new PackedObjectInfo(pos
	}

    private void updateTempObjectIdWithDigestForPackedObject(int type
        tempObjectId.fromRaw(digestForPackedObject(type
    }

    private byte[] digestForPackedObject(int type
        objectDigest.update(Constants.encodedTypeString(type));
        objectDigest.update((byte) ' ');
        objectDigest.update(Constants.encodeASCII(sz));
        objectDigest.update((byte) 0);
        DigestOutputStream digestOutputStream = new DigestOutputStream(inflatedDataDestination
        inflate(Source.INPUT
        digestOutputStream.flush();
        return objectDigest.digest();
    }

    private void verifySafeObject(final AnyObjectId id
			final byte[] data) throws IOException {

