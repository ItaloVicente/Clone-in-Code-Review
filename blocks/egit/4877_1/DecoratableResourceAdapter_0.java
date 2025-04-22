			switch (resource.getType()) {
			case IResource.FILE:
				extractResourceProperties();
				break;
			case IResource.PROJECT:
				tracked = true;
			case IResource.FOLDER:
				extractContainerProperties();
				break;
			}
