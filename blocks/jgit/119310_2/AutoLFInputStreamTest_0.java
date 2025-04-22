		try (InputStream bis1 = new ByteArrayInputStream(input);
				InputStream cis1 = new AutoLFInputStream(bis1
			int index1 = 0;
			for (int b = cis1.read(); b != -1; b = cis1.read()) {
				assertEquals(expected[index1]
				index1++;
			}
