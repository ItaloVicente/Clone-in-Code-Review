				try {
					if (shouldClone) {
						int timeout = 60;
						String refName = Constants.R_HEADS + branch;
						final CloneOperation cloneOperation = new CloneOperation(
								gitUrl, true, null, workDir.toFile(), refName,
								Constants.DEFAULT_REMOTE_NAME, timeout);
						cloneOperation.run(monitor);
					}
