		try (Scanner scanner = new Scanner(file.getContents())) {
			scanner.useDelimiter("\\A");
			String fileContent = "";
			if (scanner.hasNext()) {
				fileContent = scanner.next();
			}
			assertEquals(expectedContents, fileContent);
