		Comparator<IExtension> comparer = new Comparator<IExtension>() {
			@Override
			public int compare(IExtension arg0, IExtension arg1) {
				String s1 = arg0.getContributor().getName();
				String s2 = arg1.getContributor().getName();
				return s1.compareToIgnoreCase(s2);
			}
