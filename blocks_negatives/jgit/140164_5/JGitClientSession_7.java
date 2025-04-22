			if (d.getPackSource() == GC) {
				assertEquals(packSize0 + packSize1 - 32,
						d.getEstimatedPackSize());
			} else if (d.getPackSource() == UNREACHABLE_GARBAGE) {
				assertEquals(packSize1, d.getEstimatedPackSize());
			} else {
				fail("unexpected " + d.getPackSource());
			}
