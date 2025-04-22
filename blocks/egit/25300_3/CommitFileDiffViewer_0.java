						.getAbsolutePath()).append(diff.getPath())
							.toOSString();
				boolean workTreeFileExists = new File(path).exists();
				compareWorkingTreeVersion.setEnabled(workTreeFileExists);
				openWorkingTreeVersion.setEnabled(workTreeFileExists);
			} else {
