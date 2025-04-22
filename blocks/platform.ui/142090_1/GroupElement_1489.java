		return getContents().toArray();
	}

	private ArrayList<OrganizationElement> getContents() {
		if (contents == null)
			contents = new ArrayList<>();
		return contents;
	}

	@Override
