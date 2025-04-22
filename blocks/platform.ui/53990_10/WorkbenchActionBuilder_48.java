        prefListener = event -> {
		    if (event.getProperty().equals(
		            ResourcesPlugin.PREF_AUTO_BUILDING)) {
		       	updateBuildActions(false);
		    }
		};
