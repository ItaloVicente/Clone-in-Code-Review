				while (resourcesEnum.hasNext()) {
					try {
						IResource resource = resourcesEnum.next();
						refreshResource(resource, subMonitor.newChild(1));
					} catch (CoreException e) {
						errors = recordError(errors, e);
