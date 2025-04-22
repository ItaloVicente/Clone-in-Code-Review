		v.getControl().addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(TraverseEvent e) {
				if( (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS) && mgr.getFocusCell().getColumnIndex() == 2 ) {
					ColumnViewerEditor editor = v.getColumnViewerEditor();
					ViewerCell cell = mgr.getFocusCell();

					try {
						Method m = ColumnViewerEditor.class.getDeclaredMethod("processTraverseEvent", new Class[] {int.class,ViewerRow.class,TraverseEvent.class});
						m.setAccessible(true);
						m.invoke(editor, Integer.valueOf(cell.getColumnIndex()), cell.getViewerRow(), e);
					} catch (SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e1) {
						e1.printStackTrace();
					}
