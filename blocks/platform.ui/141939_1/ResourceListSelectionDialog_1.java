			int last;
			if ((patternString.indexOf('?') == -1) && (patternString.endsWith("*")) && //$NON-NLS-1$
					(patternString.indexOf('*') == patternString.length() - 1)) {
				firstMatch = getFirstMatch();
				if (firstMatch == -1) {
					firstMatch = 0;
					lastMatch = -1;
				} else {
					lastMatch = getLastMatch();
				}
				last = lastMatch;
				for (int i = firstMatch; i <= lastMatch; i++) {
					if (i % 50 == 0) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}
					}
					if (stop || resourceNames.isDisposed()) {
						disposed[0] = true;
						return;
					}
					final int index = i;
					display.syncExec(() -> {
						if (stop || resourceNames.isDisposed()) {
