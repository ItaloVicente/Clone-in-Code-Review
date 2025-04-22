		JXPathContext context,
		QName name,
		int index) {
		NodePointer ptr = (NodePointer) clone();
		ptr.setIndex(index);
		return ptr.createPath(context);
	}
