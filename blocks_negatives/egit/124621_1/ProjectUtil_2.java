			SubMonitor progress = SubMonitor.convert(monitor,
					CoreText.ProjectUtil_refreshing, resources.length);
			for (IResource resource : resources) {
				if (progress.isCanceled())
					break;
				resource.refreshLocal(IResource.DEPTH_INFINITE,
						progress.newChild(1));
			}
