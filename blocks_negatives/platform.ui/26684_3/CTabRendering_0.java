		Rectangle partHeaderBounds = computeTrim(PART_HEADER, state, bounds.x,
				bounds.y, bounds.width, bounds.height);
		
		drawUnselectedTabBackground(gc, partHeaderBounds, state, vertical,
				defaultBackground);
		drawTabBackground(gc, partHeaderBounds, state, vertical,
				defaultBackground);
