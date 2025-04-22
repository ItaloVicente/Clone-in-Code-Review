				String projectFilePath = p.getLocation().append(".project").toOSString();  //$NON-NLS-1$
				File projectFile = new File(projectFilePath);
				if (projectFile.exists())
						p.refreshLocal(IResource.DEPTH_INFINITE,
								new SubProgressMonitor(monitor, 1));

				 else
					p.delete(false, true, new SubProgressMonitor(monitor, 1));

				monitor.worked(1);
