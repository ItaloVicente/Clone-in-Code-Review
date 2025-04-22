			if (member.getType() == IResource.FILE) {
				String repoWorkDir = repo.getWorkDir().toString();
				String memberRelPath = member.getLocation().toString();
				memberRelPath = memberRelPath.replace(repoWorkDir, ""); //$NON-NLS-1$
				if (memberRelPath.startsWith(File.separator)) {
					memberRelPath = memberRelPath.substring(1);
				}
