				boolean workTreeFileExists = false;
				if (diff.getChange() != ChangeType.DELETE) {
					String path = new Path(getRepository().getWorkTree()
							.getAbsolutePath()).append(diff.getNewPath())
							.toOSString();
					workTreeFileExists = new File(path).exists();
				}
				compareWorkingTreeVersion.setEnabled(workTreeFileExists);
				openWorkingTreeVersion.setEnabled(workTreeFileExists);
			} else {
