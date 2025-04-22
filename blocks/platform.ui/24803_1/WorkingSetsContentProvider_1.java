			} else {
				children = getWorkingSetElements(workingSet);
			}
			if (workingSet == manager.getDefaultWorkingSet()) {
				List<IAdaptable> res = new ArrayList<IAdaptable>(children.length);
				res.addAll(Arrays.asList(children));
				for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
					if (helper.getParent(project) == null) {
						res.add(project);
					}
				}
				children = res.toArray(new IAdaptable[res.size()]);
