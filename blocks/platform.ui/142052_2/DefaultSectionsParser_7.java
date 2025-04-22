	protected IAdaptable getParent(Hashtable<String, MarkElement> toc, String number) {
		int lastDot = number.lastIndexOf('.');
		if (lastDot < 0)
			return null;
		String parentNumber = number.substring(0, lastDot);
		return toc.get(parentNumber);
	}
