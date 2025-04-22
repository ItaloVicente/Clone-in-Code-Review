		Region clipping = new Region();
		gc.getClipping(clipping);
		r.intersect(clipping);
		gc.setClipping(r);
		Rectangle mappedBounds = display.map(parent, parent.getParent(), bounds);
		parent.getParent().drawBackground(gc, bounds.x, bounds.y, bounds.width, bounds.height, mappedBounds.x,
				mappedBounds.y);
