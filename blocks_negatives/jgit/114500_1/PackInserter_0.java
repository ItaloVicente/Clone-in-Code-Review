			file.seek(0);
			write(hdrBuf, 0, writePackHeader(hdrBuf, objectList.size()));

			byte[] buf = buffer();
			SHA1 md = digest().reset();
			file.seek(0);
			while (true) {
				int r = file.read(buf);
				if (r < 0) {
					break;
