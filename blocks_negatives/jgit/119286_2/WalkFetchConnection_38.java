			try {
				final FileOutputStream fos = new FileOutputStream(tmpIdx);
				try {
					final byte[] buf = new byte[2048];
					int cnt;
					while (!pm.isCancelled() && (cnt = s.in.read(buf)) >= 0) {
						fos.write(buf, 0, cnt);
						pm.update(cnt / 1024);
					}
				} finally {
					fos.close();
