				if (ownCfg.isFile()
						&& FileKey.isGitRepository(ownCfg.getParentFile(), fs)) {
					register(c, ownCfg.getParentFile());
				}
				if (c instanceof IProject) {
					File p = fsLoc.getParentFile();
					while (p != null) {
						if (GitTraceLocation.CORE.isActive())
							GitTraceLocation.getTrace().trace(
									GitTraceLocation.CORE.getLocation(),
											+ p);
						final File pCfg = configFor(p);
						if (pCfg.isFile()
								&& FileKey.isGitRepository(
										pCfg.getParentFile(), fs)) {
							register(c, pCfg.getParentFile());
						}
						if (ceilingDirectories.contains(p.getPath()))
							break;
						p = p.getParentFile();
					}
				}
