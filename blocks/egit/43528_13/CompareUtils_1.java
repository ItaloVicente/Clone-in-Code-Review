				Set<IResource> resSet = new LinkedHashSet<>(resources.length);
				for (IResource res : resources) {
					resSet.add(res);
				}
				ResourceMappingContext mappingContext = prepareContext(
						repository, leftRev, rightRev, includeLocal, resSet);
				return !canDirectlyOpenInCompare((IFile) resource, mappingContext);
