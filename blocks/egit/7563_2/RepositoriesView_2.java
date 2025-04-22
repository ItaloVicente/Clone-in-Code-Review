			List<IResource> resources = new ArrayList<IResource>();
			for (Iterator it = ss.iterator(); it.hasNext();) {
				Object element = it.next();
				IResource resource = AdapterUtils.adapt(element, IResource.class);
				if (resource != null)
					resources.add(resource);
			}
			if (!resources.isEmpty()) {
				showResources(resources);
				return true;
