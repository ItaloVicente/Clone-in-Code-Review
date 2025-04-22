			ResourceMapping mapping = AdapterUtils.adapt(o,
					ResourceMapping.class);
			if (mapping != null) {
				for (IResource r : extractResourcesFromMapping(mapping)) {
					IPath l = r.getLocation();
					if (l != null)
						result.add(l);
				}
