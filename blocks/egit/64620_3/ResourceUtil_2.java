		public static boolean isKnownGitProject(@NonNull IProject project) {
			synchronized (INSTANCE.projects) {
				return INSTANCE.projects.contains(project);
			}
		}

