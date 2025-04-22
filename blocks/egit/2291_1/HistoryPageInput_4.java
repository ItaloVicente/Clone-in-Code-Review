		if (resourceItems.length == 1) {
			singleItem = resourceItems[0];
			if (resourceItems[0].getType() == IResource.FILE)
				singleFile = resourceItems[0];
			else
				singleFile = null;
		} else {
			singleItem = null;
