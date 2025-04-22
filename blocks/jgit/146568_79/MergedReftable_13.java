			if (last != null) {
				if (last.maxUpdateIndex() >= t.minUpdateIndex()) {
					throw new IllegalStateException(MessageFormat.format(
							JGitText.get().indexNumbersNotIncreasing
							Integer.valueOf(i)
							Long.valueOf(t.minUpdateIndex())
							Long.valueOf(last.maxUpdateIndex())));
				}
			}
			last = t;

