		Reader r = new InputStreamReader(new FileInputStream(f), "UTF-8");
		try {
			char[] data = new char[checkData.length()];
			if (checkData.length() != r.read(data))
				throw new IOException("Internal error reading file data from "+f);
			assertEquals(checkData, new String(data));
		} finally {
			r.close();
