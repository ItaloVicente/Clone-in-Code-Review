		try (Reader r = new InputStreamReader(new FileInputStream(f)
				"UTF-8")) {
			if (checkData.length() > 0) {
				char[] data = new char[checkData.length()];
				assertEquals(data.length
				assertEquals(checkData
			}
			assertEquals(-1
