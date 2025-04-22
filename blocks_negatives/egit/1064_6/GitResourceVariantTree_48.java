	private String getMemberRelPath(Repository repo, IResource member) {
		String repoWorkDir = repo.getWorkDir().toString();
			repoWorkDir = repoWorkDir.replace(File.separatorChar, '/');
		}

		String memberRelPath = member.getLocation().toString();
		memberRelPath = memberRelPath.replace(repoWorkDir, ""); //$NON-NLS-1$
			memberRelPath = memberRelPath.substring(1);

		return memberRelPath;
