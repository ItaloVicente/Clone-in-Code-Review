		List<Rectangle> adjustedFrames = new ArrayList<>();

		for (Rectangle next : frames) {
			adjustedFrames.add(Geometry.toControl(overlayFrame, next));
		}

