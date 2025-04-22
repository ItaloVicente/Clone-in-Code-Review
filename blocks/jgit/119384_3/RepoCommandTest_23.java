		try (BufferedReader reader = new BufferedReader(
				new FileReader(dotmodules))) {
			boolean foo = false;
			boolean foobar = false;
			boolean a = false;
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				if (line.contains("submodule \"foo\""))
					foo = true;
				if (line.contains("submodule \"foo/bar\""))
					foobar = true;
				if (line.contains("submodule \"a\""))
					a = true;
			}
			assertTrue("The foo submodule should exist"
			assertFalse("The foo/bar submodule shouldn't exist"
			assertTrue("The a submodule should exist"
