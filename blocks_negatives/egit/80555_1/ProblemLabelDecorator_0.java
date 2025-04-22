			Object[] elements = contentProvider.getElements(null);
			for (Object element : elements) {
				IResource resource = AdapterUtils.adapt(element, IResource.class);
				if (resource != null && resources.contains(resource))
					result.add(element);
			}
