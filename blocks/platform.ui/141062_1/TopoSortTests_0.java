
	static class CycleCausingExceptionSorter extends TopologicalSort<Integer, Integer> {

		public Integer[] getTestData() {
			return new Integer[] { 1, 2, 3, 4, 5 };
		}

		@Override
		protected Integer getId(Integer object) {
			return object;
		}

		@Override
		protected Collection<Integer> getRequirements(Integer id) {
			switch (id) {
			case 1:
				return Arrays.asList(2);
			case 2:
				return Arrays.asList(3, 4);
			case 3:
				return Arrays.asList(4);
			case 4:
				return Arrays.asList(1, 5);
			case 5:
				return Arrays.asList(1);
			default:
				throw new IllegalStateException();
			}
		}

		@Override
		protected Collection<Integer> getDependencies(Integer id) {
			switch (id) {
			case 1:
				return Arrays.asList(4, 5);
			case 2:
				return Arrays.asList(1);
			case 3:
				return Arrays.asList(2);
			case 4:
				return Arrays.asList(3, 2);
			case 5:
				return Arrays.asList(4);
			default:
				throw new IllegalStateException();
			}
		}
	}

	@Test
	public void testSorter() {
		CycleCausingExceptionSorter sorter = new CycleCausingExceptionSorter();
		Integer[] sort = sorter.sort(sorter.getTestData());
		assertNotNull(sort);
	}

