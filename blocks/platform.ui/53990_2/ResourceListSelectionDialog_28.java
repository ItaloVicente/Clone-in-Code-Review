        BusyIndicator.showWhile(getShell().getDisplay(), () -> {
		    getMatchingResources(resources);
		    IResource resourcesArray[] = new IResource[resources.size()];
		    resources.toArray(resourcesArray);
		    initDescriptors(resourcesArray);
		});
