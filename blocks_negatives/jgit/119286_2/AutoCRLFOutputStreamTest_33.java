			} else if (i < 0) {
				int n;
				while ((n = in.read(buf)) >= 0) {
					byte[] b = new byte[n];
					System.arraycopy(buf, 0, b, 0, n);
					out.write(b);
				}
			} else {
				int c;
				while ((c = in.read()) != -1)
					out.write(c);
