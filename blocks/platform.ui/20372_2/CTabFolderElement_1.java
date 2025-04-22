
	@Override
	public void reset() {
		super.reset();
		CTabFolder folder = (CTabFolder) getWidget();
		folder.setSelectionBackground((Color) null);
		folder.setSelectionForeground((Color) null);
		folder.setBackground(null, null);

		CTabFolderRenderer renderer = folder.getRenderer();
		if (renderer != null
				&& !renderer.getClass().getName()
				.equals(CTabFolderRenderer.class.getName())) {
			try {
				Method m = renderer.getClass().getMethod("setSelectedTabFill", new Class[] { Color.class });
				m.invoke(renderer, (Color) null);

				m = renderer.getClass().getMethod("setTabOutline", new Class[] { Color.class });
				m.invoke(renderer, (Color) null);

				m = renderer.getClass().getMethod("setInnerKeyline", new Class[] { Color.class });
				m.invoke(renderer, (Color) null);

				m = renderer.getClass().getMethod("setOuterKeyline", new Class[] { Color.class });
				m.invoke(renderer, (Color) null);

				m = renderer.getClass().getMethod("setShadowColor", new Class[] { Color.class });
				m.invoke(renderer, (Color) null);

				m = renderer.getClass().getMethod("setActiveToolbarGradient", new Class[] { Color[].class, int[].class });
				m.invoke(renderer, null, null);

				m = renderer.getClass().getMethod("setInactiveToolbarGradient", new Class[] { Color[].class, int[].class });
				m.invoke(renderer, null, null);
			} catch (Exception e) {
			}

			folder.setRenderer(null);
		}
	}
