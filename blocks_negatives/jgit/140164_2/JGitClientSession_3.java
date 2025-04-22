			if (d.getPackSource() == GC) {
				gcPackFound = true;
			} else if (d.getPackSource() == INSERT) {
				insertPackFound = true;
			} else {
				fail("unexpected " + d.getPackSource());
			}
