			private Object[] getFolderChildren(ProjectFolder projectFolder) {
				ArrayList<Object> list = new ArrayList<Object>();
				list.addAll(projectFolder.getSubfolders());
				list.addAll(projectFolder.getProjects());
				return list.toArray(new Object[projectFolder.getSubfolders().size() + projectFolder.getProjects().size()]);
			}

