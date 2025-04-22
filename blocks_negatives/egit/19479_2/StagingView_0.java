
			private void showControl(Control c, final boolean show) {
				c.setVisible(show);
				GridData g = (GridData) c.getLayoutData();
				g.exclude = !show;
			}
