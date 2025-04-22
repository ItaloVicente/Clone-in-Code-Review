	private static void assertDequoteMinimal(String exp
		final byte[] b = ('"' + in + '"').getBytes(ISO_8859_1);
		final String r = GIT_PATH_MINIMAL.dequote(b
		assertEquals(exp
	}

