		if (containers.length == 1) {
			if (containers[0].isAccessible())
				return new ContainerTreeIterator(this, containers[0]);
		} else {
			IContainer container = findContainer(containers);
			if (container != null)
				return new ContainerTreeIterator(this, container);
		}
		return new AdaptableFileTreeIterator(this, currentFile, root);
	}

	private IContainer findContainer(IContainer[] containers) {
		List<IContainer> validContainers = new ArrayList<IContainer>(containers.length);
		for (IContainer container : containers) {
			if (container.isAccessible())
				validContainers.add(container);
		}
		if (validContainers.size()==0)
			return null;
		if (validContainers.size()==1)
			return validContainers.get(0);
		Collections.sort(validContainers, new Comparator<IContainer>(){
			public int compare(IContainer o1, IContainer o2) {
				IPath location1 = o1.getProject().getLocation();
				IPath location2 = o2.getProject().getLocation();
				if(location1.equals(location2))
					return 0;
				if(location1.isPrefixOf(location2))
					return -1;
				return 1;
			}});
		return validContainers.get(validContainers.size()-1);
