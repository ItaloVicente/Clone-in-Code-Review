	@SuppressWarnings("unchecked")
	public <T> T getAdapter(Class<T> adapter) {
		if (adapter == CommonViewer.class) {
			return (T) getCommonViewer();
		} else if (adapter == INavigatorContentService.class) {
			return (T) getCommonViewer().getNavigatorContentService();
		} else if (adapter == IShowInTarget.class) {
			return (T) this;
		} else if (adapter == IShowInSource.class) {
			return (T) getShowInSource();
