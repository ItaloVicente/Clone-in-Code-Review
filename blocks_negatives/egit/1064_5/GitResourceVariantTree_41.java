			if (resource.getLocation().toFile().equals(file)) {
				try {
					Tree merge = trees.get(repository);
					TreeEntry tree = merge.findBlobMember(gitPath);
					GitBlobResourceVariant variant = new GitBlobResourceVariant(
							resource, repository, tree.getId(), dates
									.get(gitPath));
					return variant;
				} catch (IOException e) {
					throw new TeamException(new Status(IStatus.ERROR, Activator
							.getPluginId(), NLS.bind(
							CoreText.GitResourceVariantTree_couldNotFindBlob,
							gitPath), e));
				}
			}
		}
		return null;
	}
