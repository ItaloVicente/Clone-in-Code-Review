			if (ptr > 0) {
				if (raw[ptr - 1] == '.')
					throw new InvalidPathException(
							JGitText.get().invalidPathPeriodAtEndWindows
							t.getEntryPathString());
				if (raw[ptr - 1] == ' ')
					throw new InvalidPathException(
							JGitText.get().invalidPathSpaceAtEndWindows
							t.getEntryPathString());
			}

