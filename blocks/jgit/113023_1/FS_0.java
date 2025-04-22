				final boolean processExited = waitForProcessCompletion();
				if (!processExited) {
					setError(e
					fail.set(true);
				} else if (p.exitValue() != 0) {
					setError(e
