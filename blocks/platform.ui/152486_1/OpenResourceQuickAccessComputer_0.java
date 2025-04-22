				if (resourceProxy.getType() == IResource.FILE) {
					String name = resourceProxy.getName();
					if (name.regionMatches(true, 0, query, 0, query.length())) {
						IResource resource = resourceProxy.requestResource();
						res.add(new ResourceElement(labelProvider, resource));
					}
