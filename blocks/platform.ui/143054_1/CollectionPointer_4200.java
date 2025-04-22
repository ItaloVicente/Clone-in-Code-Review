		JXPathContext context,
		QName name,
		int index,
		Object value) {
		NodePointer ptr = (NodePointer) clone();
		ptr.setIndex(index);
		return ptr.createPath(context, value);
	}
