	private Tree getTreeWidget() {
		TreeViewer viewer;
		try {
			Method m = ExtendedMarkersView.class.getDeclaredMethod("getViewer");
			m.setAccessible(true);
			viewer = (TreeViewer) m.invoke(this);
			return viewer.getTree();
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
