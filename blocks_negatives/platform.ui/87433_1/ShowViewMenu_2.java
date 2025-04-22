	private Comparator actionComparator = new Comparator() {
		@Override
		public int compare(Object o1, Object o2) {
			if (collator == null) {
				collator = Collator.getInstance();
			}
			CommandContributionItemParameter a1 = (CommandContributionItemParameter) o1;
			CommandContributionItemParameter a2 = (CommandContributionItemParameter) o2;
			return collator.compare(a1.label, a2.label);
