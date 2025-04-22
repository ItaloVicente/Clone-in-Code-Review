		if ((resource.getKind() & RIGHT) == RIGHT)
			return CompareUtils.getFileRevisionTypedElement(resource.getGitPath(),
					resource.getRemoteCommit(), resource.getRepository(),
					resource.getRemoteId());
		else
			return CompareUtils.getFileRevisionTypedElement(resource.getGitPath(),
				resource.getBaseCommit(), resource.getRepository(),
				resource.getBaseId());

