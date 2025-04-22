			if (rm != null) {
				String repoRelativePath = rm.getRepoRelativePath(r);
				if (repoRelativePath != null)
					if (repoRelativePath.equals("")) //$NON-NLS-1$
						return TreeFilter.ALL;
					else
						filters.add(PathFilter.create(repoRelativePath));
			}
