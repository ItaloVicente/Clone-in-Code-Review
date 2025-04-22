	private void assertNoMatches(TreeWalk tw) throws MissingObjectException
			IncorrectObjectTypeException
		assertMatches(Sets.<String> of()
	}

	private void assertMatches(Set<String> expect
			throws MissingObjectException
			IOException {
		List<String> actual = new ArrayList<>();
		for (String path : singles.keySet()) {
			if (includes(singles.get(path)
				actual.add(path);
			}
		}

		String[] e = expect.toArray(new String[expect.size()]);
		String[] a = actual.toArray(new String[actual.size()]);
		Arrays.sort(e);
		Arrays.sort(a);
		assertArrayEquals(e

		if (expect.isEmpty()) {
			assertFalse(includes(filter
		} else {
			assertTrue(includes(filter
		}
	}

	private static boolean includes(TreeFilter f
			throws MissingObjectException
			IOException {
		try {
			return f.include(tw);
		} catch (StopWalkException e) {
			return false;
		}
	}

