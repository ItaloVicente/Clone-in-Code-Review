		ResourceMappingContext mappingContext = prepareContext(repository,
				leftRev, rightRev, includeLocal);
		if (resources.length == 1
				&& resources[0] instanceof IFile
				&& canDirectlyOpenInCompare((IFile) resources[0],
						mappingContext)) {
