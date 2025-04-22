				contentDigest.update((byte) 0);

				for (;;) {
					final int r = is.read(contentReadBuffer);
					if (r <= 0)
						break;
					contentDigest.update(contentReadBuffer, 0, r);
					sz += r;
				}
				if (sz != blobLength)
					return zeroid;
				return contentDigest.digest();
			} finally {
