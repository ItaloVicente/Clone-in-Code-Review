	public void processFragmentWrappers(Collection<ModelFragmentWrapper> wrappers) {
		Map<String, Bucket> elementIdToBucket = new LinkedHashMap<>();
		Map<String, Bucket> parentIdToBuckets = new LinkedHashMap<>();
		for (ModelFragmentWrapper fragmentWrapper : wrappers) {
			MModelFragment fragment = fragmentWrapper.getModelFragment();
			String parentId = MStringModelFragment.class.cast(fragment).getParentElementId();
			if (!parentIdToBuckets.containsKey(parentId)) {
				parentIdToBuckets.put(parentId, new Bucket());
			}
			Bucket b = parentIdToBuckets.get(parentId);
			if (elementIdToBucket.containsKey(parentId)) {
				Bucket parentBucket = elementIdToBucket.get(parentId);
				parentBucket.dependencies.add(b);
				b.dependentOn = parentBucket;
			}
			b.wrapper.add(fragmentWrapper); // $NON-NLS-1$

			for (MApplicationElement e : fragment.getElements()) {
				if (parentId == e.getElementId()) {
					continue;
				}
				elementIdToBucket.put(e.getElementId(), b);
				b.containedElementIds.add(e.getElementId());
				if (parentIdToBuckets.containsKey(e.getElementId())) {
					Bucket childBucket = parentIdToBuckets.get(e.getElementId());
					b.dependencies.add(childBucket);
					childBucket.dependentOn = b;
				}
			}
		}
		processFragments(createUnifiedFragmentList(elementIdToBucket));
	}

	private List<ModelFragmentWrapper> createUnifiedFragmentList(Map<String, Bucket> elementIdToBucket) {
		List<ModelFragmentWrapper> fragmentList = new ArrayList<>();
		Set<String> checkedElementIds = new LinkedHashSet<>();
		for (String elementId : elementIdToBucket.keySet()) {
			if (checkedElementIds.contains(elementId))
				continue;
			Bucket bucket = elementIdToBucket.get(elementId);
			while (bucket.dependentOn != null) {
				bucket = bucket.dependentOn;
			}
			addAllBucketFragmentWrapper(bucket, fragmentList, checkedElementIds);
		}
		return fragmentList;
	}

	private void addAllBucketFragmentWrapper(Bucket bucket, List<ModelFragmentWrapper> fragmentList,
			Set<String> checkedElementIds) {
		for (ModelFragmentWrapper wrapper : bucket.wrapper)
			fragmentList.add(wrapper);
		checkedElementIds.addAll(bucket.containedElementIds);
		for (Bucket child : bucket.dependencies) {
			addAllBucketFragmentWrapper(child, fragmentList, checkedElementIds);
		}
	}

	public void processFragments(Collection<ModelFragmentWrapper> fragmentList) {
