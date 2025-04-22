	public Object getAdapter(Class adapter) {
		if (adapter == COMMON_VIEWER_CLASS) {
			return getCommonViewer();
		} else if (adapter == INAVIGATOR_CONTENT_SERVICE) {
			return getCommonViewer().getNavigatorContentService();
		} else if (adapter == ISHOW_IN_TARGET_CLASS) {
			return this;
		} else if (adapter == ISHOW_IN_SOURCE_CLASS) {
            return getShowInSource();
