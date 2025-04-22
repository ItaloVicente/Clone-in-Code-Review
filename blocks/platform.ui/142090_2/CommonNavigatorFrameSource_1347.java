	private CommonNavigator navigator;

	public CommonNavigatorFrameSource(CommonNavigator navigator) {
		super(navigator.getCommonViewer());
		this.navigator = navigator;
	}

	@Override
