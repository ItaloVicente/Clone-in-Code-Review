		IllegalArgumentException e = assertThrows(
				IllegalArgumentException.class
				() -> CommitBuilder.writeGpgSignatureString(signature
						new ByteArrayOutputStream()));
		String message = MessageFormat.format(JGitText.get().notASCIIString
				signature);
		assertEquals(message
