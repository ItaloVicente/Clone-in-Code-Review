			if (file == null) {
				Repository repository = null;
				GitModelObject modelObject = obj;
				while (modelObject != null) {
					if (modelObject instanceof GitModelRepository) {
						repository = ((GitModelRepository) modelObject)
								.getRepository();
						break;
					}
					modelObject = modelObject.getParent();
				}
				left = new LocalNonWorkspaceTypedElement(repository,
						obj.getLocation());
			} else {
