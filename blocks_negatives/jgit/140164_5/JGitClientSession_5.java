			if (d.getPackSource() == GC) {
				gcPackFound = true;
				gcPackSize = d.getFileSize(PACK);
			} else if (d.getPackSource() == GC_REST) {
				gcRestPackFound = true;
				gcRestPackSize = d.getFileSize(PACK);
			} else if (d.getPackSource() == INSERT) {
				insertPackFound = true;
				insertPackSize = d.getFileSize(PACK);
			} else {
				fail("unexpected " + d.getPackSource());
			}
