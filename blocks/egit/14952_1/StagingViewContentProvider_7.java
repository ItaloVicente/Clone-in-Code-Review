		List<Object> children = new ArrayList<Object>();
		for (StagingFolderEntry folder : getCompressedFolders()) {
			if (folder.getFile().getParentFile() != null
					&& folder.getFile().getParentFile()
							.equals(parent.getFile())) {
				folder.setParent(parent);
				children.add(folder);
			}
		}
