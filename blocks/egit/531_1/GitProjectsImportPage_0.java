	private HashSet<Object> getCheckedProjects() {
		HashSet<Object> ret = new HashSet<Object>();
		for (TreeItem item : projectsList.getTree().getItems()) {
			if (item.getChecked()) {
				ret.add(item.getData());
			}
		}
		return ret;
	}
}
