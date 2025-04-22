	public void testCheckoutHierarchy() throws IOException {
		setupCase(
				mkmap("a"
						"e/g")
				mkmap("a"
						"e/g2")
				mkmap("a"
						"e/g3"));
		try {
			checkout();
		} catch (CheckoutConflictException e) {
			assertWorkDir(mkmap("a"
					"e/f"
			assertConflict("e/g");
		}
	}

