				assertTrue("The .gitattributes file should exist",
						gitattributes.exists());
				try (BufferedReader reader = new BufferedReader(
						new FileReader(gitattributes));) {
					String content = reader.readLine();
					assertEquals(".gitattributes content should be as expected",
