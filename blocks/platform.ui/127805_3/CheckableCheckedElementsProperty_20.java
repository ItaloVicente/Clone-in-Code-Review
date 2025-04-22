	protected void doUpdateSet(S source, SetDiff<Object> diff) {
		ICheckable checkable = source;
		for (Object e : diff.getAdditions())
			checkable.setChecked(e, true);
		for (Object e : diff.getRemovals())
			checkable.setChecked(e, false);
