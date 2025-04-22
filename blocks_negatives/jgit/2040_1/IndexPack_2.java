		final byte[] data = inflateAndReturn(Source.INPUT, sz);
		objectDigest.update(Constants.encodedTypeString(type));
		objectDigest.update((byte) ' ');
		objectDigest.update(Constants.encodeASCII(sz));
		objectDigest.update((byte) 0);
		objectDigest.update(data);
		tempObjectId.fromRaw(objectDigest.digest(), 0);

		verifySafeObject(tempObjectId, type, data);
		final int crc32 = (int) crc.getValue();
		addObjectAndTrack(new PackedObjectInfo(pos, crc32, tempObjectId));
	}
