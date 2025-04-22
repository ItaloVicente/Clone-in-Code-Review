		String[] list = myConfig.getStringList(RepositoriesView.REMOTE, myName,
				(String) id);
		if (list != null && list.length > 1) {
			StringBuilder sb = new StringBuilder();
			for (String s : list) {
				sb.append('[');
				sb.append(s);
				sb.append(']');
			}
			return sb.toString();
