		if (c instanceof IProject) {
			UnmapJob unmapJob = new UnmapJob((IProject) c);
			unmapJob.schedule();
		} else if (c != null) {
			try {
				c.setSessionProperty(MAPPING_KEY, null);
			} catch (CoreException e) {
				Activator.logWarning(MessageFormat.format(
						CoreText.GitProjectData_failedToUnmapRepoMapping,
						c.getFullPath()), e);
			}
		}
