
		int side = Geometry.getClosestSide(bb, pointToTest);


		boolean horizontal = Geometry.isHorizontal(side);
		int size = Geometry.getCoordinate(Geometry.getSize(bb), !horizontal);
		Rectangle overlayBounds = Geometry.getExtrudedEdge(bb, size / 2, side);
		System.out.println("Setting overlay to " + overlayBounds);
		dndManager.frameRect(Geometry.copy(overlayBounds));
