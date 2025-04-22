	protected StyledString getStyledTextForSubmodules(SubmodulesNode node) {
		String label = getSimpleText(node);
		if (label == null) {
			return new StyledString();
		}
		StyledString styled = new StyledString(label);
		Repository repository = node.getRepository();
		if (repository != null) {
			boolean hasChanges = false;
			try (SubmoduleWalk walk = SubmoduleWalk.forIndex(repository)) {
				while (!hasChanges && walk.next()) {
					Repository submodule = walk.getRepository();
					if (submodule != null) {
						Repository cached = org.eclipse.egit.core.Activator
								.getDefault().getRepositoryCache()
								.lookupRepository(submodule.getDirectory()
										.getAbsoluteFile());
						hasChanges = cached != null
								&& RepositoryUtil.hasChanges(cached);
						submodule.close();
					}
				}
			} catch (IOException e) {
				hasChanges = false;
			}
			if (hasChanges) {
				StyledString prefixed = new StyledString();
				prefixed.append('>', StyledString.DECORATIONS_STYLER);
				prefixed.append(' ').append(styled);
				return prefixed;
			}
		}
		return styled;
	}

