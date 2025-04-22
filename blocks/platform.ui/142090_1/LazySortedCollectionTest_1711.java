		System.out.println("Comparisons required by lazy collection: " + comparator.comparisons);
		System.out.println("Comparisons required by reference implementation: " + comparisonComparator.comparisons);
		System.out.println("");

		super.tearDown();
	}

	private Object[] computeExpectedElementsInRange(int start, int length) {
		int counter = 0;
