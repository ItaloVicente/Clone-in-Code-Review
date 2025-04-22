				IProject project = roots.get(filePath);
				if (project != null) {
					handled.put(filePath, null);
					result.put(project, Boolean.FALSE);
					progress.worked(1);
					return;
				}
