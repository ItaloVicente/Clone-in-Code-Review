		final Repository repository = getRepository(true, event);
		if (repository == null)
			return null;
		IResource[] resources = getSelectedResources();
		PatchOperationUI.createPatch(getPart(event), getRepository(),
				Arrays.asList(resources)).start();
