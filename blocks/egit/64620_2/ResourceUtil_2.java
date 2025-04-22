	private static void markAsUnshared(@NonNull IProject project) {
		try {
			project.setSessionProperty(MARKED_AS_UNSHARED, PROJECT_IS_UNSHARED);
		} catch (CoreException e) {
		}
	}

	private static void markAsShared(@NonNull IProject project) {
		try {
			project.setSessionProperty(MARKED_AS_UNSHARED, null);
		} catch (CoreException e) {
		}
	}

	private static boolean isMarkedAsUnshared(@NonNull IProject project) {
		try {
			return project.getSessionProperty(
					MARKED_AS_UNSHARED) == PROJECT_IS_UNSHARED;
		} catch (CoreException e) {
			return false;
		}
	}

