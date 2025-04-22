			if (fsync) {
				FileChannel fc = os.getChannel();
				ByteBuffer buf = ByteBuffer.wrap(content);
				while (0 < buf.remaining())
					fc.write(buf);
				fc.force(true);
			} else {
				os.write(content);
			}
