		for (Iterator<INavigatorContentDescriptor> iter = dropDescriptors.keySet().iterator(); iter
				.hasNext();) {
			INavigatorContentDescriptor contentDescriptor = iter
					.next();
			if (aContentService.isVisible(contentDescriptor.getId())
					&& aContentService.isActive(contentDescriptor.getId())) {
				List<CommonDropAdapterDescriptor> dropDescriptors = getDropDescriptors(contentDescriptor);
				for (Iterator<CommonDropAdapterDescriptor> iterator = dropDescriptors.iterator(); iterator
						.hasNext();) {
					CommonDropAdapterDescriptor dropDescriptor = iterator
							.next();
					if (dropDescriptor.isDropElementSupported(aDropTarget)) {
						foundDescriptors.add(dropDescriptor);
					}
				}
			}
		}
