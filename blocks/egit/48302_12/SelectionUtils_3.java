				IResource resource = AdapterUtils.adapt(o, IResource.class);
				if (resource != null) {
					result.add(resource);
				} else {
					IPath location = AdapterUtils.adapt(o, IPath.class);
					if (location != null) {
						result.add(location);
					}
