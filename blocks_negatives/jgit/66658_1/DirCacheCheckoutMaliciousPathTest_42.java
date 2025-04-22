			if (!good)
				fail("Checkout of Tree " + Arrays.asList(path) + " should fail");
		} catch (InvalidPathException e) {
			if (good)
				throw e;
			assertTrue(e.getMessage().startsWith("Invalid path"));
