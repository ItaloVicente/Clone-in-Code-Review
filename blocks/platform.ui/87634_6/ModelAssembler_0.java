	private class Bucket {
		SortedSet<ModelFragmentWrapper> wrapper = new TreeSet<>(new ModelFragmentComparator(application));
		Bucket dependentOn;
		Set<Bucket> dependencies = new LinkedHashSet<>();
		Set<String> containedElementIds = new LinkedHashSet<>();
	}

