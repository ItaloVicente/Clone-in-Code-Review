		else {
			String container = m.getContainerPath().toString();
			if (container.length() > 0)
				container += File.separator;
			treeItem2.setText(2, container + m.getGitDir());
		}
