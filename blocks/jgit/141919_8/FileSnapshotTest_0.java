		assertTrue("unexpected change of fileKey"
		assertFalse("unexpected size change"
		assertFalse("unexpected lastModified change"
				save.wasLastModifiedChanged());
		assertFalse("lastModified was unexpectedly racily clean"
				save.wasLastModifiedRacilyClean());
