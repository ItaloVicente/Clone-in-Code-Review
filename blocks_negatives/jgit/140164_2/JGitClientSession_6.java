			if (d.getPackSource() == GC) {
				gcPackFound = true;
				assertEquals(gcPackSize + insertPackSize - 32,
						pack.getPackDescription().getEstimatedPackSize());
			} else if (d.getPackSource() == GC_REST) {
				gcRestPackFound = true;
				assertEquals(gcRestPackSize + insertPackSize - 32,
						pack.getPackDescription().getEstimatedPackSize());
			} else {
				fail("unexpected " + d.getPackSource());
			}
