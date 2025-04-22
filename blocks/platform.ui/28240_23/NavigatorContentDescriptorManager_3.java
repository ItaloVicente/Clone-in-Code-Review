		if (Policy.DEBUG_RESOLUTION) {
			System.out.println("Find descriptors for: " + Policy.getObjectString(anElement) + //$NON-NLS-1$
					(considerOverrides ? " (with overrides)" : "") + ": " + descriptors); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		cache.setDescriptors(anElement, descriptors.toArray(new NavigatorContentDescriptor[descriptors.size()]), considerOverrides);

