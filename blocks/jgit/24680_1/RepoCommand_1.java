				if (inGroups(proj)) {
					String url = remoteUrl + proj.name;
					command.addSubmodule(url
				}
			}
		}

		boolean inGroups(Project proj) {
			for (String group : minusGroups) {
				if (proj.groups.contains(group)) {
					return false;
				}
			}
			if (plusGroups.isEmpty() || plusGroups.contains("all")) {
				return true;
			}
			for (String group : plusGroups) {
				if (proj.groups.contains(group))
					return true;
