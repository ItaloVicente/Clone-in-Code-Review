	private void checkTapKeys(HashMap<String, Boolean> items) {
		for (Entry<String, Boolean> kv : items.entrySet()) {
			if (!kv.getValue().booleanValue()) {
				fail();
			}
		}
	}
