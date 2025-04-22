	/**
	 * The interface these tests need from a class implementing a checkout
	 */
	interface Checkout {
		HashMap<String, ObjectId> updated();
		ArrayList<String> conflicts();
		ArrayList<String> removed();
		void prescanTwoTrees() throws IOException;
		void checkout() throws IOException;
	}

