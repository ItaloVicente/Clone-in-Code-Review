			ResourcesPlugin.getWorkspace().run(pm -> {
				SubMonitor progress = SubMonitor.convert(pm,
						CoreText.ProjectUtil_refreshing, resources.length);
				for (IResource resource : resources) {
					if (progress.isCanceled())
						break;
					resource.refreshLocal(IResource.DEPTH_INFINITE,
							progress.newChild(1));
				}
			}, null, IWorkspace.AVOID_UPDATE, monitor);
