		try {
			int totalRead = 0;
			int length = 0;
			if (in != null) {
				byte[] buf = new byte[8192];
				while ((length = in.read(buf)) != -1) {
					out.write(buf
					totalRead += length;
