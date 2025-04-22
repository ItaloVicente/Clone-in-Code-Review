		Control[] existingControls = control.getChildren();
		Control lastControl = null;
		int exIndex = 0;
		for (int i = 0; i < limit; i++) {
			JobTreeElement jobElement = (JobTreeElement) toShowJobElements[i];
			ProgressInfoItem item = jobItemControls.get(jobElement);
			if (item == null) {
				item = createNewItem(jobElement);
				jobItemControls.put(jobElement, item);

				if (existingControls.length != 0) {
					if (lastControl == null) {
						item.moveAbove(null);
					} else {
						item.moveBelow(lastControl);
					}
				}
			} else {
				for (; exIndex < existingControls.length; exIndex++) {
					if (existingControls[exIndex] != null && !existingControls[exIndex].isDisposed()) {
						break;
					}
				}
				if (exIndex < existingControls.length && existingControls[exIndex] != item) {
					if (exIndex == 0) {
						item.moveAbove(null);
					} else {
						item.moveBelow(existingControls[exIndex - 1]);
					}
					for (int j = exIndex + 1; j < existingControls.length; j++) {
						if (existingControls[j] == item) {
							existingControls[j] = null;
							break;
						}
					}
				} else {
					exIndex++;
				}
			}
