		}
	}

	private static boolean shouldUseTreeCompare(IResource[] resources) {
		if (resources.length == 1) {
			IResource resource = resources[0];
			if (resource instanceof IFile) {
				return !canDirectlyOpenInCompare((IFile) resource);
			} else {
				IPath location = resource.getLocation();
				if (location != null
						&& Files.isSymbolicLink(location.toFile().toPath())) {
					return false;
				}
			}
		}
		return true;
