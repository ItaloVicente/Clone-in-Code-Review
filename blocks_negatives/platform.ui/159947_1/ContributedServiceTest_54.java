		progress = part.getSite().getService(
				IProgressService.class);
		assertFalse(progress == getWorkbench().getProgressService());
		assertEquals(part.getSite().getService(
				IWorkbenchSiteProgressService.class), progress);
		assertEquals(part.getSite().getAdapter(
				IWorkbenchSiteProgressService.class), progress);
