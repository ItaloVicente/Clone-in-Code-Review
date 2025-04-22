			Repository r = AdapterUtils.adapt(o, Repository.class);
			if (r != null) {
				result.add(r);
				continue;
			}
			IResource resource = AdapterUtils.adaptToAnyResource(o);
