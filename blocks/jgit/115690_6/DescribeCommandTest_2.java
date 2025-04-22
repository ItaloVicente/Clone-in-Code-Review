		return Arrays.asList(new Boolean[][] {
				{ Boolean.TRUE
				{ Boolean.FALSE
				{ Boolean.TRUE
				{ Boolean.FALSE
		});
	}

	private static void touch(File f
		FileWriter w = new FileWriter(f);
		w.write(contents);
		w.close();
	}

	private static void assertNameStartsWith(ObjectId c4
		assertTrue(c4.name()
