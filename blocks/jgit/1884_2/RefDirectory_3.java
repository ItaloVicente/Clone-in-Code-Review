				if (wc.getFSyncRefFiles()) {
					FileChannel fc = out.getChannel();
					ByteBuffer buf = ByteBuffer.wrap(rec);
					while (0 < buf.remaining())
						fc.write(buf);
					fc.force(true);
				} else {
					out.write(rec);
				}
