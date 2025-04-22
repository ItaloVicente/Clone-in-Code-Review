		try {
			getterCalled();
		} catch (Exception e) {
		}
		Set<E> removes;
		try {
			removes = new FakeSet<>(wrappedSet);
		} catch (Exception e) {
			e.printStackTrace();
			removes = new HashSet<>();
		} finally {
			wrappedSet.clear();
		}
		fireSetChange(Diffs.createSetDiff(Collections.emptySet(), removes));
