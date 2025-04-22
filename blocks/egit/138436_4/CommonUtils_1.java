	public static final Comparator<Ref> REF_ASCENDING_COMPARATOR = new Comparator<Ref>() {
		@Override
		public int compare(Ref o1, Ref o2) {
			String name1 = o1.getName();
			String name2 = o2.getName();
			if (name1.startsWith(Constants.R_TAGS)
					&& name2.startsWith(Constants.R_TAGS)
					&& !sortTagsAscending) {
				name1 = name2;
				name2 = o1.getName();
			}
			return STRING_ASCENDING_COMPARATOR.compare(name1, name2);
		}
	};

	public static final Comparator<String> TAG_STRING_COMPARATOR = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			if (sortTagsAscending) {
				return STRING_ASCENDING_COMPARATOR.compare(o1, o2);
			} else {
				return STRING_ASCENDING_COMPARATOR.compare(o2, o1);
			}
		}
	};
