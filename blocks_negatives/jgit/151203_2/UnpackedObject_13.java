				if (path == null && Integer.MAX_VALUE < size) {
					LargeObjectException.ExceedsByteArrayLimit e;
					e = new LargeObjectException.ExceedsByteArrayLimit();
					e.setObjectId(id);
					throw e;
				}
				if (size < wc.getStreamFileThreshold() || path == null) {
					in.reset();
					IO.skipFully(in, p);
					Inflater inf = wc.inflater();
					InputStream zIn = inflate(in, inf);
					byte[] data = new byte[(int) size];
					IO.readFully(zIn, data, 0, data.length);
					checkValidEndOfStream(in, inf, id, hdr);
					return new ObjectLoader.SmallObject(type, data);
				}
				return new LargeObject(type, size, path, id, wc.db);
