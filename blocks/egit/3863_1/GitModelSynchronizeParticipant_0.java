		} else {
			String lastSelectedModel = preferenceStore.getString(UIPreferences.SYNC_VIEW_LAST_SELECTED_MODEL);
			if (!"".equals(lastSelectedModel)) //$NON-NLS-1$
				modelProvider = lastSelectedModel;
			else
				modelProvider = WORKSPACE_MODEL_PROVIDER_ID;
		}

