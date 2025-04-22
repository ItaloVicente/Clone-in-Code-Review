				Repository submoduleRepo = walk.getRepository();

				if (submoduleRepo == null || walk.getModulesPath() == null
						|| walk.getConfigUrl() == null) {
					continue;
				}
