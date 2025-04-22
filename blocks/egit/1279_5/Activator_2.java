

			if (Activator.getDefault().getPreferenceStore()
					.getBoolean(UIPreferences.REFESH_ONLY_WHEN_ACTIVE)) {
				if (!isActive()) {
					monitor.done();
					if (doReschedule)
						schedule(REPO_SCAN_INTERVAL);
					return Status.OK_STATUS;
				}
			}

