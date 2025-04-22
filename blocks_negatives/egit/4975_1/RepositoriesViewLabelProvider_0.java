				if (node.getRepository().isBare()) {
					dirString
							.append(
									UIText.RepositoriesViewLabelProvider_BareRepositoryMessage,
									StyledString.QUALIFIER_STYLER);
				} else {
					dirString.append(node.getRepository().getWorkTree()
							.getAbsolutePath(), StyledString.QUALIFIER_STYLER);
				}
