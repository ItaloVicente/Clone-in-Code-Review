		for (INavigatorContentDescriptor iNavigatorContentDescriptor : theDescriptors) {
NavigatorContentExtension extension = getExtension(
			iNavigatorContentDescriptor,
			toLoadAllIfNecessary);
if (extension != null) {
		resultInstances.add(extension);
