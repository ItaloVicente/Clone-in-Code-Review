			boolean hasRebuildSetting = settings.get(STORE_REBUILD_CREATED_PROJECTS_ID) != null;
			if (hasRebuildSetting) {
				rebuildProjectsAfterImport = settings.getBoolean(STORE_REBUILD_CREATED_PROJECTS_ID);
				rebuildProjectsCheckbox.setSelection(rebuildProjectsAfterImport);
			}

