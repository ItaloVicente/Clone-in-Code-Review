						if (recordShallowSubmodules && proj.getRecommendShallow() != null) {
							cfg.setBoolean("submodule", path, "shallow", //$NON-NLS-1$ //$NON-NLS-2$
									true);
						}
					}
					if (recordSubmoduleLabels) {
						StringBuilder rec = new StringBuilder();
						rec.append(path);
						for (String group : proj.getGroups()) {
							rec.append(group);
