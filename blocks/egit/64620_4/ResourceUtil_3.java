	private static void markAsUnshared(@NonNull IProject project) {
		try {
			project.setSessionProperty(PROVIDER_ID, PROJECT_IS_UNSHARED);
		} catch (CoreException e) {
		}
	}

	private static void markAsShared(@NonNull IProject project,
			@Nullable String providerId) {
		try {
			project.setSessionProperty(PROVIDER_ID, providerId);
		} catch (CoreException e) {
		}
	}

	private static boolean isMarkedAsNotSharedWithGit(
			@NonNull IProject project) {
		try {
			Object property = project.getSessionProperty(PROVIDER_ID);
			if (property == PROJECT_IS_UNSHARED) {
				return true;
			} else if (property instanceof String
					&& !GitProvider.ID.equals(property)) {
				return true;
			}
		} catch (CoreException e) {
		}
		return false;
	}

