				editButton.setEnabled(enabled);
				removeButton.setEnabled(enabled);
			}
		}
	}

	private void updateWidgetState() {
		variableTable.refresh();
		updateEnabledState();
	}

	public void setResource(IResource resource) {
		currentResource = resource;
		if (resource != null)
			pathVariableManager = resource.getPathVariableManager();
		else
			pathVariableManager = ResourcesPlugin.getWorkspace().getPathVariableManager();
