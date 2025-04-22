			objectDigest.update(Constants.encodedTypeString(type));
			objectDigest.update((byte) ' ');
			objectDigest.update(Constants.encodeASCII(data.length));
			objectDigest.update((byte) 0);
			objectDigest.update(data);
			tempObjectId.fromRaw(objectDigest.digest(), 0);
