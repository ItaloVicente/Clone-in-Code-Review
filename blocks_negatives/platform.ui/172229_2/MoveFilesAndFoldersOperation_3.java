			} else {
				if (existing != null) {
					if (homogenousResources(resource, existing)) {
						moveExisting(resource, existing, iterationMonitor.split(100));
					} else {
						delete(existing, iterationMonitor.split(50));
						resource.move(destinationPath, IResource.SHALLOW | IResource.KEEP_HISTORY,
								iterationMonitor.split(50));
					}
