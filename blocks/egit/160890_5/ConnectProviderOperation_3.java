			project.accept((IResourceProxy resource) -> {
				int type = resource.getType();
				if ((type == IResource.FILE || type == IResource.FOLDER)
						&& Constants.DOT_GIT.equals(resource.getName())) {
					progress.setWorkRemaining(2);
					resource.requestResource().touch(progress.newChild(1));
					return false;
