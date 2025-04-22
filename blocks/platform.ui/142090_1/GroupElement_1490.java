		return this.toString();
	}

	public String getErrorMessage() {
		return null;
	}

	private ArrayList<OrganizationElement> getSubGroups() {
		if (subGroups == null)
			subGroups = new ArrayList<>();
		return subGroups;
	}

	private ArrayList<OrganizationElement> getUsers() {
		if (users == null)
			users = new ArrayList<>();
		return users;
	}

	@Override
