			IProject[] mappedProjects = mapping.getProjects();
			if (mappedProjects != null && mappedProjects.length != 0) {
				List<IProject> projects = new ArrayList<IProject>(
						Arrays.asList(mappedProjects));
				Collections
						.sort(projects, CommonUtils.RESOURCE_NAME_COMPARATOR);
				ret.addAll(projects);
			}
