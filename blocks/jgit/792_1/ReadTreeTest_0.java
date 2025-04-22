
	interface Checkout {
		HashMap<String
		ArrayList<String> conflicts();
		ArrayList<String> removed();
		void prescanTwoTrees() throws IOException;
		void checkout() throws IOException;
	}

	protected Checkout getCheckoutImpl(Tree head
			Tree merge) {
		return new WorkdirCheckoutImpl(head
	}

	class WorkdirCheckoutImpl extends WorkDirCheckout implements Checkout {
		public WorkdirCheckoutImpl(Tree head
				Tree merge) {
			super(db
		}

		public HashMap<String
			return updated;
		}

		public ArrayList<String> conflicts() {
			return conflicts;
		}

		public ArrayList<String> removed() {
			return removed;
		}

		public void prescanTwoTrees() throws IOException {
			super.prescanTwoTrees();
		}
	}
