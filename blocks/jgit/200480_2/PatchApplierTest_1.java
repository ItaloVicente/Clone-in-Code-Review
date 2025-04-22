		@Test
		public void testConflictMarkers() throws Exception {
			init("Conflict"

			Result result = applyPatchAllowConflicts();
			assertEquals(result.getPathsWithConflicts()
			verifyChange(result
		}

		@Test
		public void testConflictMarkersOutOfBounds() throws Exception {
			init("ConflictOutOfBounds"

			Result result = applyPatchAllowConflicts();
			assertEquals(result.getPathsWithConflicts()
			verifyChange(result
		}

