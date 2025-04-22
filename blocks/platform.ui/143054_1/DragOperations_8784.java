		return new Point(0, 0);
	}

	public static Point getPoint(Rectangle bounds, int side) {
		Point centerPoint = Geometry.centerPoint(bounds);

		switch (side) {
		case SWT.TOP:
			return new Point(centerPoint.x, bounds.y + 1);
		case SWT.BOTTOM:
			return new Point(centerPoint.x, bounds.y + bounds.height - 1);
		case SWT.LEFT:
			return new Point(bounds.x + 1, centerPoint.y);
		case SWT.RIGHT:
			return new Point(bounds.x + bounds.width - 1, centerPoint.y);
		}

		return centerPoint;
	}

	public static String nameForConstant(int swtSideConstant) {
		switch (swtSideConstant) {
		case SWT.TOP:
			return "top";
		case SWT.BOTTOM:
			return "bottom";
		case SWT.LEFT:
			return "left";
		case SWT.RIGHT:
			return "right";
		}

		return "center";
	}

	public static String getName(IViewPart targetPart) {
		return targetPart.getTitle();
	}

	public static String getLayoutDescription(WorkbenchPage page) {
		StringBuilder buf = new StringBuilder();

		buf.append("this layout still not quite described - TODO");
		return buf.toString();
	}
