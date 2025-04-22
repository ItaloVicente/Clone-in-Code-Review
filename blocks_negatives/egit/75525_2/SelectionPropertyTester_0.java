				IWorkingSet workingSet = AdapterUtils.adapt(element,
						IWorkingSet.class);
				if (workingSet != null) {
					for (IAdaptable adaptable : workingSet.getElements()) {
						Repository r = getRepositoryOfProject(adaptable);
						if (single && r != null && repo != null && r != repo)
							return null;
						else if (r != null)
							repo = r;
