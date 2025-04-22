		Method m;
		try {
			m = ColumnViewer.class.getDeclaredMethod("applyEditorValue", new Class[0]);
			m.setAccessible(true);
			m.invoke(getTableViewer(), new Object[0]);
		} catch (SecurityException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			fail(e.getMessage());

		}
