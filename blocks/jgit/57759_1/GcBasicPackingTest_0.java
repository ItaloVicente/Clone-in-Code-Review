	@Test
	public void testCommitRangeForBitmaps() throws Exception {
		BranchBuilder bb1 = tr.branch("refs/heads/master");
		bb1.commit().message("A1").add("A1"
		bb1.commit().message("B1").add("B1"
		bb1.commit().message("C1").add("C1"
		BranchBuilder bb2 = tr.branch("refs/heads/working");
		bb2.commit().message("A2").add("A2"
		bb2.commit().message("B2").add("B2"
		bb2.commit().message("C2").add("C2"

		configureGcRange(gc
		gc.gc();
		assertEquals(6

		configureGcRange(gc
		gc.gc();
		assertEquals(2

		configureGcRange(gc
		gc.gc();
		assertEquals(2

		configureGcRange(gc
		gc.gc();
		assertEquals(3

		configureGcRange(gc
		gc.gc();
		assertEquals(4

		configureGcRange(gc
		gc.gc();
		assertEquals(4

		configureGcRange(gc
		gc.gc();
		assertEquals(5

		configureGcRange(gc
		gc.gc();
		assertEquals(6

		configureGcRange(gc
		gc.gc();
		assertEquals(6
	}

	private void configureGcRange(GC myGc
		PackConfig pconfig = new PackConfig(repo);
		pconfig.setBitmapCommitRange(range);
		myGc.setPackConfig(pconfig);
	}

