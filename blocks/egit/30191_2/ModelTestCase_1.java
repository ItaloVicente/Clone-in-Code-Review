		Scanner scanner = null;
		try {
			scanner = new Scanner(file.getContents()).useDelimiter("\\A");

			String fileContent = "";
			if (scanner.hasNext()) {
				fileContent = scanner.next();
			}

			assertEquals(expectedContents, fileContent);
		} finally {
			if (scanner != null)
				scanner.close();
