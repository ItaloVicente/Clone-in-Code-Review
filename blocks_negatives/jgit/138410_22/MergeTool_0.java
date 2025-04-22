		for (String fileName : fileNames) {
			StageState fileState = files.get(fileName);
			if (fileState == StageState.BOTH_MODIFIED) {
				boolean launch = true;
				if (showPrompt) {
					launch = isLaunch(toolNamePrompt);
				}
				if (launch) {
				} else {
					break;
