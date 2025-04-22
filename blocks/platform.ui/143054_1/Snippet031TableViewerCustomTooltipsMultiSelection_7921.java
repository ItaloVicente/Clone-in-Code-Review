				switch (event.type) {
				case SWT.KeyDown:
				case SWT.Dispose:
				case SWT.MouseMove: {
					if (tooltip == null) break;
					tooltip.dispose ();
					tooltip = null;
					label = null;
					break;
				}
				case SWT.MouseHover: {
					Point coords = new Point(event.x, event.y);
					TableItem item = table.getItem(coords);
					if (item != null) {
						int columnCount = table.getColumnCount();
						for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
							if (item.getBounds(columnIndex).contains(coords)) {
								if (tooltip != null  && !tooltip.isDisposed ()) tooltip.dispose ();

								tooltip = new Shell (table.getShell(), SWT.ON_TOP | SWT.NO_FOCUS | SWT.TOOL);
								tooltip.setBackground (table.getDisplay().getSystemColor (SWT.COLOR_INFO_BACKGROUND));
								FillLayout layout = new FillLayout ();
								layout.marginWidth = 2;
								tooltip.setLayout (layout);
								label = new Label (tooltip, SWT.NONE);
								label.setForeground (table.getDisplay().getSystemColor (SWT.COLOR_INFO_FOREGROUND));
								label.setBackground (table.getDisplay().getSystemColor (SWT.COLOR_INFO_BACKGROUND));

								label.setData ("_TableItem_", item);

								label.setText("Tooltip: " + item.getData() + " : " + columnIndex);

								label.addListener (SWT.MouseExit, tooltipLabelListener);
								label.addListener (SWT.MouseDown, tooltipLabelListener);

								Point size = tooltip.computeSize (SWT.DEFAULT, SWT.DEFAULT);
								Rectangle rect = item.getBounds (columnIndex);
								Point pt = table.toDisplay (rect.x, rect.y);
								tooltip.setBounds (pt.x, pt.y, size.x, size.y);

								tooltip.setVisible (true);
								break;
							}
						}
					}
				}
				}
			}
