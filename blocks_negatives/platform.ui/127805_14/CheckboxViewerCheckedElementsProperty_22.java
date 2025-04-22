	protected void doUpdateSet(Object source, SetDiff diff) {
		ICheckable checkable = (ICheckable) source;
		for (Iterator it = diff.getAdditions().iterator(); it.hasNext();)
			checkable.setChecked(it.next(), true);
		for (Iterator it = diff.getRemovals().iterator(); it.hasNext();)
			checkable.setChecked(it.next(), false);
