			if (input instanceof IResource[]) {
				IResource[] resources = (IResource[]) input;
				if (resources.length == 1)
					name = (resources[0]).getFullPath().makeRelative()
							.toString();
				else
					name = UIText.CompareTreeView_MultipleResourcesHeaderText;
			} else if (input instanceof Repository)
