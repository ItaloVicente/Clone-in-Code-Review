
		walk.reset();
		walk.sort(RevSort.TOPO);

		BitmapIndex repoBitmaps = walk.getObjectReader().getBitmapIndex();
		ReachedFilter reachedFilter = new ReachedFilter(repoBitmaps);
		walk.setRevFilter(reachedFilter);

