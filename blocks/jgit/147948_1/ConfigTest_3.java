
		Map<String

		assertEquals(!isWindows
		assertTrue((Boolean.valueOf(options.get("core.logallrefupdates"))));
		if (isMac) {
			assertTrue(
					(Boolean.valueOf(options.get("core.precomposeunicode"))));
		}
		assertEquals(Integer.valueOf(0)
				Integer.valueOf(options.get("core.repositoryformatversion")));
	}

	private Map<String
		Map<String
		Arrays.stream(output).forEachOrdered(s -> {
			int p = s.indexOf('=');
			if (p == -1) {
				return;
			}
			String key = s.substring(0
			String value = s.substring(p + 1);
			options.put(key
		});
		return options;
