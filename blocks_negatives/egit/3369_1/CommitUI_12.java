			try {
				if (!filename.startsWith(repoRelativePath))
					continue;
				String projectRelativePath = filename
						.substring(repoRelativePath.length());
				IFile member = project.getFile(projectRelativePath);
				if (!files.contains(member))
					files.add(member);
				category.add(member);
			} catch (Exception e) {
				if (GitTraceLocation.UI.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.UI.getLocation(), e.getMessage(),
							e);
				continue;
