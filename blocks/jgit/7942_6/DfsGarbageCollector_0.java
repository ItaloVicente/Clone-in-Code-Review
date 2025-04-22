		if (pw.prepareIndexBitmaps(pm)) {
			out = objdb.writeFile(pack
			try {
				CountingOutputStream cnt = new CountingOutputStream(out);
				pw.writeIndex(cnt);
				pack.setFileSize(BITMAP_INDEX
			} finally {
				out.close();
			}
		}

