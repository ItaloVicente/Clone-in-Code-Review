	public void setNewName(String name) throws NoHeadException
		newTree = getRevision(name);
	}

	public void setOldName(String name) throws NoHeadException
		oldTree = getRevision(name);
	}

