    private static final Comparator comparer = new Comparator() {
        @Override
		public int compare(Object arg0, Object arg1) {
			IExtension i1 = (IExtension) arg0;
			String s1 = i1.getNamespace();
			IExtension i2 = (IExtension) arg1;
			String s2 = i2.getNamespace();
			return s1.compareToIgnoreCase(s2);
		}
    };
