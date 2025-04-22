
		List<String> expectedWithoutDuplicates = new ArrayList<String>(
				new LinkedHashSet<String>(expected));
		List<String> shuffeled = new ArrayList<String>(expected);
		Collections.shuffle(shuffeled, new Random(1));
		TreeSet<String> sortedSet = new TreeSet<String>(
				CommonUtils.STRING_ASCENDING_COMPARATOR);
		sortedSet.addAll(shuffeled);
		assertEquals(expectedWithoutDuplicates,
				new ArrayList<String>(sortedSet));
