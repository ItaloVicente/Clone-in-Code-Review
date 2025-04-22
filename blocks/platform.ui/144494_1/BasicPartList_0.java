
	@Override
	protected boolean isCloseable(TableItem tItem) {
		Object obj = tItem.getData();
		if (obj instanceof MPart) {
			return renderer.isClosable((MPart) obj);
		}
		return super.isCloseable(tItem);
	}
