			if (targetPartId == null) {
				return false;
			}
			if (WorkbenchPlugin.getDefault().getViewRegistry().find(targetPartId) == null) {
				return false;
			}
