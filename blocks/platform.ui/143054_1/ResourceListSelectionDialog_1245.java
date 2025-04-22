		updateGatherThread.stop = true;
		updateGatherThread = new UpdateGatherThread();

		if (patternString.isEmpty()) {
			updateGatherThread.start();
			return;
		}
		stringMatcher = new StringMatcher(patternString, true, false);

		if (oldPattern != null && (oldPattern.length() != 0) && oldPattern.endsWith("*") //$NON-NLS-1$
				&& patternString.endsWith("*")) { //$NON-NLS-1$
			int matchLength = oldPattern.length() - 1;
			if (patternString.regionMatches(0, oldPattern, 0, matchLength)) {
				updateGatherThread.refilter = true;
				updateGatherThread.firstMatch = 0;
				updateGatherThread.lastMatch = descriptorsSize - 1;
				updateGatherThread.start();
				return;
			}
		}
