				Ref ref = repo.findRef(oldName);
				if (ref == null)
					throw new RefNotFoundException(MessageFormat.format(
							JGitText.get().refNotResolved, oldName));
				if (ref.getName().startsWith(Constants.R_TAGS))
					throw new RefNotFoundException(MessageFormat.format(
							JGitText.get().renameBranchFailedBecauseTag,
							oldName));
