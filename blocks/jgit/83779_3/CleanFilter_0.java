			byte[] buf = new byte[8192];
			int length = in.read(buf);
			if (length != -1) {
				dOut.write(buf
				size += length;
				return length;
