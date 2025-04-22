				String changedFilePath = null;
				if (leftResource != null) {
					changedFilePath = repository.getWorkTree().toPath()
							.relativize(leftResource.getRawLocation().toFile().toPath())
							.toString();
				} else if (leftRevision != null) {
					changedFilePath = leftRevision.getPath();
				}
