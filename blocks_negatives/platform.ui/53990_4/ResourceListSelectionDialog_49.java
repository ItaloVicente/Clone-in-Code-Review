        BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
            @Override
			public void run() {
                getMatchingResources(resources);
                IResource resourcesArray[] = new IResource[resources.size()];
                resources.toArray(resourcesArray);
                initDescriptors(resourcesArray);
            }
        });
