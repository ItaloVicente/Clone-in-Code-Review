
			BufferedReader reader1 = new BufferedReader(new FileReader(linkedhello));
			String content1 = reader1.readLine();
			reader1.close();
			assertEquals("The LinkedHello file should have expected content",
					"Content doesn't matter to a nihilist!", content1);
