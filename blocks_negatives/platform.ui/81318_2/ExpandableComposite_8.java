			toggle.addPaintListener(new PaintListener() {
				@Override
				public void paintControl(PaintEvent e) {
					if (textLabel instanceof Label && !isFixedStyle())
						textLabel.setForeground(toggle.hover ? toggle
								.getHoverDecorationColor()
								: getTitleBarForeground());
				}
