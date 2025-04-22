
			if (projectDescriptionLocation != null) {
				Path projectDescriptionFile = projectDescriptionLocation.toFile().toPath();
				if (Files.exists(projectDescriptionFile)) {
					Files.delete(projectDescriptionFile);
				}
			}
