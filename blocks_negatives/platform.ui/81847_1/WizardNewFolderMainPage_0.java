			if (resourceGroup.getProblemType() == ResourceAndContainerGroup.PROBLEM_RESOURCE_EMPTY
					|| resourceGroup.getProblemType() == ResourceAndContainerGroup.PROBLEM_CONTAINER_EMPTY) {
				setMessage(resourceGroup.getProblemMessage());
				setErrorMessage(null);
			} else {
				setErrorMessage(resourceGroup.getProblemMessage());
			}
