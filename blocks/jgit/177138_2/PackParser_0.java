		} else if (type == Constants.OBJ_BLOB) {
			byte[] readBuffer = buffer();
			long cnt = 0;
			try (InputStream inf = inflate(Source.INPUT
				while (cnt < sz) {
					int r = inf.read(readBuffer);
					if (r <= 0)
						break;
					objectDigest.update(readBuffer
					cnt += r;
				}
			}
			objectDigest.digest(tempObjectId);
			data = null;
