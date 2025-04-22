			if (notTracked.contains(file)) {
				idxEntry = index.add(repositoryMapping.getWorkDir(), new File(repositoryMapping.getWorkDir(),
						repoRelativePath));
				index.write();

			}
