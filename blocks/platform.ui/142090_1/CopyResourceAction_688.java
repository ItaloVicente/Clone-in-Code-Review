		while (resourcesEnum.hasNext()) {
			IResource currentResource = resourcesEnum.next();
			if (!currentResource.exists()) {
				return false;
			}
			if (currentResource.getType() == IResource.PROJECT) {
				return false;
			}
			IContainer parent = currentResource.getParent();
			if ((parent != null) && (!parent.equals(firstParent))) {
				return false;
			}
		}
		return true;
	}
