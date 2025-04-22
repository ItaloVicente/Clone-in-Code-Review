			for (IProject prj : projects) {
				if (checkContainerMatch(prj, file.getAbsolutePath())) {
					result.add(prj);
					break;
				}
			}
