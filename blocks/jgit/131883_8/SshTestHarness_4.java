	protected static String createKnownHostsFile(File file
			int port
		List<String> lines = Files.readAllLines(publicKey.toPath()
				StandardCharsets.UTF_8);
		assertEquals("Public key has too many lines"
		String pubKey = lines.get(0);
		String[] parts = pubKey.split("\\s+");
		assertTrue("Unexpected key content"
				parts.length == 2 || parts.length == 3);
		String keyPart = parts[0] + ' ' + parts[1];
		String line = '[' + host + "]:" + port + ' ' + keyPart;
		Files.write(file.toPath()
		return keyPart;
	}

	protected boolean hasHostKey(String host
			List<String> lines) {
		String h = '[' + host + "]:" + port;
		return lines.stream()
				.anyMatch(l -> l.contains(h) && l.contains(keyPart));
	}

