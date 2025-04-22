				Canvas canvas = (Canvas) e.widget;
				Control child = canvas.getChildren()[0];

				boolean flipXY = false;
				if (child instanceof ToolBar && (((ToolBar) child).getStyle() & SWT.VERTICAL) != 0)
					flipXY = true;
				else if (child instanceof CoolBar && (((CoolBar) child).getStyle() & SWT.VERTICAL) != 0)
					flipXY = true;

				Rectangle bb = canvas.getBounds();
				int maxX = bb.width - 1;
				int maxY = bb.height - 1;

				if (flipXY) {
					int tmp = maxX;
					maxX = maxY;
					maxY = tmp;
				}

				Color white = e.gc.getDevice().getSystemColor(SWT.COLOR_WHITE);
				Color shadow = e.gc.getDevice().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
				RGB outerRGB = ColorUtil.blend(white.getRGB(), shadow.getRGB(), blend);
				Color outerColor = new Color(e.gc.getDevice(), outerRGB);

				e.gc.setForeground(outerColor);

				drawLine(e.gc, 1, 0, maxX - 5, 0, flipXY);
				drawLine(e.gc, maxX - 4, 1, maxX - 3, 1, flipXY);
				drawLine(e.gc, maxX - 2, 2, maxX - 2, 2, flipXY);
				drawLine(e.gc, maxX - 1, 3, maxX - 1, 4, flipXY);

				drawLine(e.gc, 1, maxY, maxX - 5, maxY, flipXY);
				drawLine(e.gc, maxX - 4, maxY - 1, maxX - 3, maxY - 1, flipXY);
				drawLine(e.gc, maxX - 2, maxY - 2, maxX - 2, maxY - 2, flipXY);
				drawLine(e.gc, maxX - 1, maxY - 3, maxX - 1, maxY - 4, flipXY);

				drawLine(e.gc, 0, 1, 0, maxY - 1, flipXY);
				drawLine(e.gc, maxX, 5, maxX, maxY - 5, flipXY);

				outerColor.dispose();

				e.gc.setForeground(white);

				drawLine(e.gc, 1, 1, maxX - 5, 1, flipXY);
				drawLine(e.gc, maxX - 4, 2, maxX - 3, 2, flipXY);
				drawLine(e.gc, maxX - 3, 3, maxX - 2, 3, flipXY);
				drawLine(e.gc, maxX - 2, 4, maxX - 2, 4, flipXY);

				drawLine(e.gc, 1, maxY - 1, maxX - 5, maxY - 1, flipXY);
				drawLine(e.gc, maxX - 4, maxY - 2, maxX - 3, maxY - 2, flipXY);
				drawLine(e.gc, maxX - 3, maxY - 3, maxX - 2, maxY - 3, flipXY);
				drawLine(e.gc, maxX - 2, maxY - 4, maxX - 2, maxY - 4, flipXY);

				drawLine(e.gc, 1, 1, 1, maxY - 1, flipXY);
				drawLine(e.gc, maxX - 1, 5, maxX - 1, maxY - 5, flipXY);
			}
		});

		canvas.setLayout(new Layout() {
