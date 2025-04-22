	private void cacheDelta(ObjectToPack srcObj
		if (Integer.MAX_VALUE < bestDelta.length())
			return;

		int rawsz = (int) bestDelta.length();
		if (deltaCache.canCache(rawsz
			try {
				byte[] zbuf = new byte[deflateBound(rawsz)];

				ZipStream zs = new ZipStream(deflater()
				bestDelta.writeTo(zs
				int len = zs.finish();

				resObj.setCachedDelta(deltaCache.cache(zbuf
				resObj.setCachedSize(rawsz);
			} catch (IOException err) {
				deltaCache.credit(rawsz);
			} catch (OutOfMemoryError err) {
				deltaCache.credit(rawsz);
			}
		}
	}

	private static int deflateBound(int insz) {
		return insz + ((insz + 7) >> 3) + ((insz + 63) >> 6) + 11;
	}

