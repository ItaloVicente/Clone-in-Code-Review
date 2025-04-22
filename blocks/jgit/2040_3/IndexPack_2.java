	private void doDeferredContentChecks() throws IOException {
		final byte[] curBuffer = new byte[readBuffer.length];
		for (PackedObjectInfo obj : deferredCheckContent) {
			position(obj.getOffset());

			int c = readFrom(Source.FILE);
			final int type = (c >> 4) & 7;
			long sz = c & 15;
			int shift = 4;
			while ((c & 0x80) != 0) {
				c = readFrom(Source.FILE);
				sz += (c & 0x7f) << shift;
				shift += 7;
			}

			switch (type) {
			case Constants.OBJ_COMMIT:
			case Constants.OBJ_TREE:
			case Constants.OBJ_BLOB:
			case Constants.OBJ_TAG: {
				ObjectStream cur = readCurs.open(obj
				try {
					if (cur.getSize() != sz)
						throw new IOException(MessageFormat.format(JGitText
								.get().collisionOn
					InputStream pck = inflate(Source.FILE
					while (0 < sz) {
						int n = (int) Math.min(readBuffer.length
						IO.readFully(cur
						IO.readFully(pck
						for (int i = 0; i < n; i++) {
							if (curBuffer[i] != readBuffer[i])
								throw new IOException(MessageFormat.format(
										JGitText.get().collisionOn
						}
						sz -= n;
					}
					pck.close();
				} finally {
					cur.close();
				}
				break;
			}

			default:
				throw new IOException(MessageFormat.format(
						JGitText.get().unknownObjectType
			}
		}
	}

