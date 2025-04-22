		boolean checkContentLater = false;
		if (type == Constants.OBJ_BLOB && sz >= streamFileThreshold) {
			InputStream inf = inflate(Source.INPUT
			long cnt = 0;
			while (cnt < sz) {
				int r = inf.read(readBuffer);
				if (r <= 0)
					break;
				objectDigest.update(readBuffer
				cnt += r;
			}
			inf.close();
			tempObjectId.fromRaw(objectDigest.digest()
			checkContentLater = readCurs.has(tempObjectId);

		} else {
			final byte[] data = inflateAndReturn(Source.INPUT
			objectDigest.update(data);
			tempObjectId.fromRaw(objectDigest.digest()
			verifySafeObject(tempObjectId
		}

