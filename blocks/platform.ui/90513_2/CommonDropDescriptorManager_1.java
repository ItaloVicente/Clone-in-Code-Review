		for (INavigatorContentDescriptor contentDescriptor : dropDescriptors.keySet()) {
if (aContentService.isVisible(contentDescriptor.getId())
			&& aContentService.isActive(contentDescriptor.getId())) {
		List<CommonDropAdapterDescriptor> dropDescriptors = getDropDescriptors(contentDescriptor);
		for (CommonDropAdapterDescriptor dropDescriptor : dropDescriptors) {
if (dropDescriptor.isDropElementSupported(aDropTarget)) {
		foundDescriptors.add(dropDescriptor);
}
}
}
}
