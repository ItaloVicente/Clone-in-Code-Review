				} else {
					CompareUtils.compareWorkspaceWithRef(getRepository(), file,
							commit.getName(), null);
				}
			} else {
				IPath path = new Path(getRepository().getWorkTree()
						.getAbsolutePath()).append(p);
				File ioFile = path.toFile();
				if (ioFile.exists()) {
					CompareUtils.compareLocalWithRef(getRepository(), ioFile,
							commit.getName(), activePage);
