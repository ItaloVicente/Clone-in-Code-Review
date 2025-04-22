		checkComputedListDiff(Arrays.asList(new Object[] { "a", "b", "c" }),
				Arrays.asList(new Object[] { "a", "b" }));
		checkComputedListDiff(Arrays.asList(new Object[] { "a", "b", "c" }),
				Arrays.asList(new Object[] { "a", "c" }));
		checkComputedListDiff(Arrays.asList(new Object[] { "a", "b", "c" }),
				Arrays.asList(new Object[] { "b", "c" }));
