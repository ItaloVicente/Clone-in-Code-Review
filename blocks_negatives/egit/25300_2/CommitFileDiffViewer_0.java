				String path = new Path(getRepository().getWorkTree()
						.getAbsolutePath()).append(diff.getNewPath())
						.toOSString();
				compareWorkingTreeVersion.setEnabled(new File(path).exists()
						&& !submoduleSelected);
			} else
