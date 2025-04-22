		TreeViewer viewer;
		try {
			Method m = ExtendedMarkersView.class.getDeclaredMethod("getViewer");
			m.setAccessible(true);
			viewer = (TreeViewer) m.invoke(this);
			return viewer.getTree();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
