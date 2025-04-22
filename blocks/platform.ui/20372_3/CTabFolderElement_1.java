
	@Override
	public void reset() {
		super.reset();
		CTabFolder folder = (CTabFolder) getWidget();
		folder.setSelectionBackground((Color) null);
		folder.setSelectionForeground((Color) null);
		folder.setBackground(null, null);

		if (folder.getRenderer() instanceof ICTabRendering) {
			ICTabRendering renderer = (ICTabRendering) folder
					.getRenderer();
			folder.setRenderer(null);
			renderer.setSelectedTabFill(null);
			renderer.setTabOutline(null);
			renderer.setInnerKeyline(null);
			renderer.setOuterKeyline(null);
			renderer.setShadowColor(null);
			renderer.setActiveToolbarGradient(null, null);
			renderer.setInactiveToolbarGradient(null, null);
		}
	}
