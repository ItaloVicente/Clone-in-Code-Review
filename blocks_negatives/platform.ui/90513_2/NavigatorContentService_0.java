		for (Iterator<INavigatorContentDescriptor> descriptorIter = theDescriptors.iterator(); descriptorIter
				.hasNext();) {
			NavigatorContentExtension extension = getExtension(
					descriptorIter.next(),
					toLoadAllIfNecessary);
			if (extension != null) {
				resultInstances.add(extension);
