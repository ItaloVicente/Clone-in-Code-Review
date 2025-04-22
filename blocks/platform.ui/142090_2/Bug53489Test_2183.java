		LineNumberReader reader = new LineNumberReader(new InputStreamReader(
				textFile.getContents()));
		String currentContents = reader.readLine();
		assertTrue("'DEL' deleted more than one key.", (originalContents //$NON-NLS-1$
				.length() == (currentContents.length() + 1)));
	}
