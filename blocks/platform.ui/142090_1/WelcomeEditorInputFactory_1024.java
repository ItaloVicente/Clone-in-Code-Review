		String versionedFeatureId = memento
				.getString(WelcomeEditorInput.FEATURE_ID);
		if (versionedFeatureId == null) {
			return null;
		}
		int colonPos = versionedFeatureId.indexOf(':');
		if (colonPos == -1) {
			return null;
		}
		String featureId = versionedFeatureId.substring(0, colonPos);
		String versionId = versionedFeatureId.substring(colonPos + 1);
		AboutInfo info = AboutInfo.readFeatureInfo(featureId, versionId);
		if (info == null) {
			return null;
		}
		return new WelcomeEditorInput(info);
	}
