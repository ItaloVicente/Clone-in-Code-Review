			Object list = window.getTransientData().remove(WindowBuilder.PERSPECTIVES);
			if (list instanceof List<?>) {
				List<MPerspective> perspectiveList = (List<MPerspective>) list;
				for (MPerspective perspective : perspectiveList) {
					importToolbarsLocation(perspective);
				}
			}
