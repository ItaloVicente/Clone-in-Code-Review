		try {
			getterCalled();
		} catch (Exception e) {
		}
		Set removes;
		try {
			removes = new FakeSet(wrappedSet);
		} catch (Exception e) {
			e.printStackTrace();
			removes = new HashSet();
		} finally {
			wrappedSet.clear();
		}
