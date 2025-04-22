				final File file = p.getLocation().toFile();
				if (file.getAbsolutePath().startsWith(
						parentFile.getAbsolutePath())) {
					p.refreshLocal(IResource.DEPTH_INFINITE,
							new SubProgressMonitor(monitor, 1));
					monitor.worked(1);
				}
