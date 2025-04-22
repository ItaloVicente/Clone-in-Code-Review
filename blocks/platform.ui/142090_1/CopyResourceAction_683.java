			IResource[] resources = sources
					.toArray(new IResource[sources.size()]);
			return newOperation.validateDestination(container, resources);
		}
		return null;
	}
