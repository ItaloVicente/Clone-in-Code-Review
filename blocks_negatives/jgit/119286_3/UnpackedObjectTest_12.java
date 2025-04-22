		try {
			byte[] tmp = new byte[data.length];
			InputStream in = ol.openStream();
			try {
				IO.readFully(in, tmp, 0, tmp.length);
			} finally {
				in.close();
			}
