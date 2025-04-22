	/**
	 * Return the current implementation of the {@link Checkout} interface.
	 * <p>
	 * May be overridden by subclasses which would inherit all tests but can
	 * specify their own implementation of a Checkout
	 *
	 * @param head
	 * @param index
	 * @param merge
	 * @return the current implementation of {@link Checkout}
	 */
	protected Checkout getCheckoutImpl(Tree head, GitIndex index,
			Tree merge) {
		return new WorkdirCheckoutImpl(head, index, merge);
	}

	/**
	 * An implementation of the {@link Checkout} interface which uses WorkDirCheckout
	 */
	class WorkdirCheckoutImpl extends WorkDirCheckout implements Checkout {
		public WorkdirCheckoutImpl(Tree head, GitIndex index,
				Tree merge) {
			super(db, trash, head, index, merge);
		}

		public HashMap<String, ObjectId> updated() {
			return updated;
		}

		public ArrayList<String> conflicts() {
			return conflicts;
