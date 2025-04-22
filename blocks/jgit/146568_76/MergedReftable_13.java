			if (last != null) {
				if (last.maxUpdateIndex() >= t.minUpdateIndex()) {
					throw new IllegalStateException(
						MessageFormat.format(
							JGitText.get().indexNumbersNotIncreasing
							i
							Long.valueOf(t.minUpdateIndex())
							Long.valueOf(
								last.maxUpdateIndex())));
				}
			}
			last = t;

