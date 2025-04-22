		try {
			CommitBuilder.writeGpgSignatureString(signature,
					new ByteArrayOutputStream());
			fail("Exception expected");
		} catch (IllegalArgumentException e) {
			String message = MessageFormat.format(JGitText.get().notASCIIString,
					signature);
			assertEquals(message, e.getMessage());
		}
