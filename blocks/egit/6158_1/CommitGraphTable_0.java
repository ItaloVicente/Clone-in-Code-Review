			private void showHover(Ref ref, MouseEvent e) {
				hoverShell = new Shell(getTableView().getTable()
						.getShell(), SWT.ON_TOP | SWT.NO_FOCUS
						| SWT.TOOL);
				hoverShell.setLayout(new FillLayout());
				hoverShell.setLocation(getHoverLocation(e));
				Label label = new Label(hoverShell, SWT.NONE);
				label.setText(getHoverText(ref));
				label.setBackground(infoBackgroundColor);
				hoverShell.pack();
				hoverShell.setVisible(true);
			}

			private Point getHoverLocation(MouseEvent e) {
				Point tableLocation = getTableView().getTable().toControl(0, 0);
				return new Point(-tableLocation.x + e.x,
						-tableLocation.y + e.y - renderer.getTextHeight());
			}

			private String getHoverText(Ref r) {
