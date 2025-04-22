			ObjectLoader ldr = db.open(id.toObjectId());
			if (!ldr.isLarge())
				return ldr.getCachedBytes();

			long sz = ldr.getSize();
			if (sz < bigFileThreshold && sz < Integer.MAX_VALUE) {
				byte[] buf;
				try {
					buf = new byte[(int) sz];
				} catch (OutOfMemoryError noMemory) {
					LargeObjectException e;

					e = new LargeObjectException(id.toObjectId());
					e.initCause(noMemory);
					throw e;
				}
				InputStream in = ldr.openStream();
				try {
					IO.readFully(in
				} finally {
					in.close();
				}
				return buf;
			}
