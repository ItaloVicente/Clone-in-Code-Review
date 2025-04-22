			for (Repository repository : repos) {
				if (mapping != null && mapping.getRepository() == repository) {
					ret.add(project);
					break;
				}
			}
