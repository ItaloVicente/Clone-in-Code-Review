				ByteBuffer buf = IO.readWholeStream(new BufferedInputStream(
						response.getEntity().getContent())
				if (buf.hasArray()) {
					error = new String(buf.array()
							buf.arrayOffset() + buf.position()
							UTF_8);
				} else {
					final byte[] b = new byte[buf.remaining()];
					buf.duplicate().get(b);
					error = new String(b
