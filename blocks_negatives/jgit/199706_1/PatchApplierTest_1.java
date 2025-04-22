
			assertEquals(2, result.getPaths().size());
			assertTrue(result.getPaths().contains("RenameNoHunks"));
			assertTrue(result.getPaths().contains("nested/subdir/Renamed"));

			verifyContent(result,nested/subdir/Renamed, true);
