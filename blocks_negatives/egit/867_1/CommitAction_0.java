				IResource member = project.getFile(projectRelativePath);
				if (member != null && member instanceof IFile) {
					if (!files.contains(member))
						files.add((IFile) member);
					category.add((IFile) member);
				} else {
					if (GitTraceLocation.UI.isActive())
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.UI.getLocation(),
				}
