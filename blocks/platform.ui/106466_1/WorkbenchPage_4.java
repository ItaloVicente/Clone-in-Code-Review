		if (part != null) {
			SaveablesList modelManager = (SaveablesList) getWorkbenchWindow()
					.getService(ISaveablesLifecycleListener.class);
			Object postCloseInfo = modelManager.preCloseParts(Collections.singletonList(part), false,
					getWorkbenchWindow());
			if (postCloseInfo != null) {
				modelManager.postClose(postCloseInfo);
			}
