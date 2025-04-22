		Region clipping = new Region();
		gc.getClipping(clipping);
		Region region = new Region();
		region.add(shape);
		region.intersect(clipping);
		gc.setClipping(region);

