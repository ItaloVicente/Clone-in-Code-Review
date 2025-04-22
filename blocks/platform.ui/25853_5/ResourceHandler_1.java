	public boolean hasTopLevelWindows() {
		return hasTopLevelWindows(resource);
	}

	private boolean hasTopLevelWindows(Resource applicationResource) {
		if (applicationResource == null || applicationResource.getContents() == null) {
			return false;
		}
		MApplication application = (MApplication) applicationResource.getContents().get(0);
		return !application.getChildren().isEmpty();
	}

