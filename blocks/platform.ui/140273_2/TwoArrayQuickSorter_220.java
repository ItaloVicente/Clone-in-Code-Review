			return fIgnoreCase ? ((String) left).compareToIgnoreCase((String) right)
					: ((String) left).compareTo((String) right);
		}
	}

	public TwoArrayQuickSorter(boolean ignoreCase) {
		fComparator = new StringComparator(ignoreCase);
	}

	public TwoArrayQuickSorter(Comparator comparator) {
		fComparator = comparator;
	}

	public void sort(Object[] keys, Object[] values) {
		if ((keys == null) || (values == null)) {
			Assert.isTrue(false, "Either keys or values in null"); //$NON-NLS-1$
			return;
		}

		if (keys.length <= 1) {
