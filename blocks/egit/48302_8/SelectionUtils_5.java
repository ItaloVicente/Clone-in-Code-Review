			ResourceMapping mapping = AdapterUtils.adapt(o,
					ResourceMapping.class);
			if (mapping != null) {
				result.addAll(extractResourcesFromMapping(mapping));
			} else {
				IResource resource = AdapterUtils.adapt(o, IResource.class);
				if (resource != null)
					result.add(resource);
			}

