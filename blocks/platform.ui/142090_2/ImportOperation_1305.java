		}

		return currentFolder;
	}

	void deleteResource(IResource resource) {
		try {
			resource.delete(IResource.KEEP_HISTORY, null);
		} catch (CoreException e) {
			errorTable.add(e.getStatus());
		}
	}

	@Override
