			}
			if (e.button == 3) {
				TableItem tItem = fTableViewer.getTable().getItem(new Point(e.x, e.y));
				if (tItem != null) {
					Menu menu = new Menu(fTableViewer.getTable());
					MenuItem mItem = new MenuItem(menu, SWT.NONE);
					mItem.setText(SWTRenderersMessages.menuClose);
					mItem.addSelectionListener(SelectionListener
							.widgetSelectedAdapter(selectionEvent -> removeSelectedItem(tItem.getData())));
					menu.setVisible(true);
