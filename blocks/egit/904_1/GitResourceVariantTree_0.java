	private String getMemberRelPath(Repository repo, IResource member) {
		String repoWorkDir = repo.getWorkDir().toString();
		if (!"/".equals(File.separator)) { //$NON-NLS-1$
			repoWorkDir = repoWorkDir.replace(File.separatorChar, '/');
		}

		String memberRelPath = member.getLocation().toString();
		memberRelPath = memberRelPath.replace(repoWorkDir, ""); //$NON-NLS-1$
		if (memberRelPath.startsWith("/")) { //$NON-NLS-1$
			memberRelPath = memberRelPath.substring(1);
		}

		return memberRelPath;
	}

