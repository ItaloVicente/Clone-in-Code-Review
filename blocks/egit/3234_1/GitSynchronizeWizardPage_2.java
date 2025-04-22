				Set<IProject> projects = projMapping.get(repository);
				if (projects == null) {
					projects = new HashSet<IProject>();
					projMapping.put(repository, projects);
				}
				projects.add(project);
