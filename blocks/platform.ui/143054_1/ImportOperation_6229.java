			}
		} catch (CoreException e) {
			errorTable.add(e.getStatus());
		}
	}

	IContainer getDestinationContainerFor(Object fileSystemObject)
			throws CoreException {
		IPath pathname = new Path(provider.getFullPath(fileSystemObject));

		if (createContainerStructure) {
