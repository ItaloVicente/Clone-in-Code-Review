	private Set<Object> getCheckedProjects() {

		Set<Object> ret = new HashSet<Object>();
		for (TreeItem item : projectsList.getTree().getItems())
			if (item.getChecked())
				ret.add(item.getData());

		return ret;
	}

	void checkPage() {
        setPageComplete(!getCheckedProjects().isEmpty());
	}
