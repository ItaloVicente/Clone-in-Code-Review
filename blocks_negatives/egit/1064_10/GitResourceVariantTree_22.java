		}
		return roots.toArray(new IResource[roots.size()]);
	}

	public IResource[] members(IResource resource) throws TeamException {
		if (resource.exists() && resource instanceof IContainer) {
			GitSynchronizeData gsd = getSyncData().getData(
					resource.getProject());
			if (gsd.shouldIncludeLocal()) {
				try {
					return ((IContainer) resource).members();
				} catch (CoreException e) {
					throw new TeamException(e.getStatus());
				}
			} else {
				return getMembersAndStore(resource, gsd);
			}
		}
		return new IResource[0];
	}

	/**
	 * Returns whether this file is of interest to this resource variant tree.
	 * Due to the fact that a repository may have many, many files, we only want
	 * to retrieve and store information about files that the user is actually
	 * interested in. That is, if they only wish to synchronize on one project,
	 * then there is no reason for this tree to be storing information about
	 * other projects that are contained within the repository.
	 *
	 * @param file
	 *            the file to check
	 * @return <code>true</code> if the blob information about this file is of
	 *         interest to this tree, <code>false</code> otherwise
	 */
	private boolean contains(File file) {
		for (GitSynchronizeData gsd : gsdData) {
			if (gsd.contains(file)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Retrieves the name of the branch that this variant tree should be
	 * compared against for the given resource.
	 *
	 * @param resource
	 *            the resource that is being compared for
	 * @return the name of the target comparison branch
	 * @throws IOException
	 */
	abstract Tree getRevTree(IResource resource) throws IOException;
