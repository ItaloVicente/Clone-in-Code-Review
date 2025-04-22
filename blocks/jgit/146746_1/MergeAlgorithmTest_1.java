
	private void assertMerge(String regularMergeExpectedOutput
			String diff3MergeExpectedOutput
			String theirs) throws IOException {
		assertEquals(t(regularMergeExpectedOutput)
		assertEquals(t(diff3MergeExpectedOutput)
				mergeWithBase(base
	}

