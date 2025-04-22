	@Override
	protected Supplier<String> internalGetAttribute(String attr) {
		if ("swt-lines-visible".equals(attr)) {
			Table table = getTable();
			return () -> String.valueOf(table.getLinesVisible());
		}
		return super.internalGetAttribute(attr);
	}
