				}
				if (member.getType() == IResource.FOLDER) {
					selectAppropriateFolderContents((IContainer) member);
				}
			}
		} catch (CoreException e) {
		}
	}

	protected void selectAppropriateResources(Object resource) {
		if (selectedResources == null) {

			if (exportSpecifiedTypesRadio.getSelection()) {
