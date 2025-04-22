				IFile file = ResourceUtil.getFileForLocation(repository,
						fit.getEntryPathString());
				if (file == null)
					continue;
				if (!conflicting || useWorkspace)
					rev = new LocalFileRevision(file);
				else
