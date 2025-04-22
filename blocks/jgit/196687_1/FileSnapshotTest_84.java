		assertTrue(save.wasFileKeyChanged()
		assertFalse(save.wasSizeChanged()
		assertFalse(save.wasLastModifiedChanged()
				"unexpected lastModified change");
		assertFalse(save.wasLastModifiedRacilyClean()
				"lastModified was unexpectedly racily clean");
