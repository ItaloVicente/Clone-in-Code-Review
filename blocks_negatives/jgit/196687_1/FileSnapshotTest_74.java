		assertTrue("unexpected change of fileKey", save.wasFileKeyChanged());
		assertFalse("unexpected size change", save.wasSizeChanged());
		assertFalse("unexpected lastModified change",
				save.wasLastModifiedChanged());
		assertFalse("lastModified was unexpectedly racily clean",
				save.wasLastModifiedRacilyClean());
