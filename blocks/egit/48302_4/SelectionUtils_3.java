				IResource resource = AdapterUtils.adapt(o, IResource.class);
				if (resource != null) {
					IPath location = resource.getLocation();
					if (location != null)
						result.add(location);
				} else {
					IPath location = AdapterUtils.adapt(o, IPath.class);
					if (location != null)
						result.add(location);
				}
