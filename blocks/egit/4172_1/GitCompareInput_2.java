		if ((resource.getKind() & RIGHT) == RIGHT)
			return CompareUtils.getFileRevisionTypedElement(resource.getGitPath(),
				resource.getBaseCommit(), resource.getRepository(),
				resource.getBaseId());
		else
			return CompareUtils.getFileRevisionTypedElement(resource.getGitPath(),
					resource.getRemoteCommit(), resource.getRepository(),
					resource.getRemoteId());
