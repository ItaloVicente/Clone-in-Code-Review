		}
		return false;
	}

	@Override
	public Object[] getChildren(Object element) {
		Object[] children = (Object[]) childMap.get(element);
		if (children == null) {
			children = createChildren(element);
			childMap.put(element, children);
		}
		return children;
	}

	private Object[] createChildren(Object element) {
		if (element instanceof MApplication) {
			List<MPartDescriptor> descriptors = ((MApplication) element).getDescriptors();
			Set<String> categoryTags = new HashSet<String>();
			Set<MPartDescriptor> noCategoryDescriptors = new HashSet<MPartDescriptor>();
			for (MPartDescriptor descriptor : descriptors) {
				List<String> tags = descriptor.getTags();
				String category = null;
				boolean isView = false;
				for (String tag : tags) {
					if (tag.equals("View")) //$NON-NLS-1$
						isView = true;
					else if (tag.startsWith(CATEGORY_TAG)) {
						category = tag.substring(CATEGORY_TAG_LENGTH);
					}
				}
				if (isView) {
					if (category != null)
						categoryTags.add(category);
					else
						noCategoryDescriptors.add(descriptor);
				}
