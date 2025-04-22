			try (final FileOutputStream fos = new FileOutputStream(tmpIdx)) {
				final byte[] buf = new byte[2048];
				int cnt;
				while (!pm.isCancelled() && (cnt = s.in.read(buf)) >= 0) {
					fos.write(buf
					pm.update(cnt / 1024);
