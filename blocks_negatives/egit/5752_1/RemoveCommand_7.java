		}
		else {
			if (!projectsToDelete.isEmpty()) {
				final boolean[] confirmedCanceled = new boolean[] { false,
						false };
				Display.getDefault().syncExec(new Runnable() {
