			Object res = parents.get(element);
			if (res != null) {
				return res;
			}
			if (unassignedProjects.contains(res)) {
				return OTHERS_WORKING_SET;
			}
			return null;
