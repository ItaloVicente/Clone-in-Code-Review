
			private void onMouseHover(Event event) {
				Point coords = new Point(event.x, event.y);
				TreeItem item = viewer.getTree().getItem(coords);
				if (item != null) {
					int columns = viewer.getTree().getColumnCount();
					for (int i = 0; i < columns || i == 0; i++) {
						if (item.getBounds(i).contains(coords)) {
							if (tip != null && !tip.isDisposed())
								tip.dispose();

							int style = SWT.ON_TOP | SWT.NO_FOCUS | SWT.TOOL;
							Color backgroundColor = viewer.getTree()
									.getDisplay()
									.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
							Color foregroundColor = viewer.getTree()
									.getDisplay()
									.getSystemColor(SWT.COLOR_INFO_FOREGROUND);

							tip = new Shell(viewer.getTree().getShell(), style);
							tip.setBackground(backgroundColor);

							tip.setLayout(createFillLayout());
							label = new Label(tip, SWT.NONE);
							label.setForeground(foregroundColor);
							label.setBackground(backgroundColor);
							label.setData("_TABLEITEM", item);
							label.setText("Tooltip: " + item.getData()
									+ " => Column: " + i);

							label.addListener(SWT.MouseExit, labelListener);
							label.addListener(SWT.MouseDown, labelListener);

							Point size = tip.computeSize(SWT.DEFAULT,
									SWT.DEFAULT);
							Rectangle rect = item.getBounds(i);
							Point pt = viewer.getTree().toDisplay(rect.x,
									rect.y);

							tip.setBounds(pt.x, pt.y, size.x, size.y);
							tip.setVisible(true);
							break;
