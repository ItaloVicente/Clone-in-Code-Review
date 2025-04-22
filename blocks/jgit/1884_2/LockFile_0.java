				if (fsync) {
					FileChannel in = fis.getChannel();
					long pos = 0;
					long cnt = in.size();
					while (0 < cnt) {
						long r = os.getChannel().transferFrom(in
						pos += r;
						cnt -= r;
					}
				} else {
					final byte[] buf = new byte[2048];
					int r;
					while ((r = fis.read(buf)) >= 0)
						os.write(buf
				}
