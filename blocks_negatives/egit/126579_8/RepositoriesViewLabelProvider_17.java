			} else if (!ref.isSymbolic() || refId != null) {
				refName.append(abbreviate(refId),
						StyledString.QUALIFIER_STYLER);
			} else {
				refName.append(
						UIText.RepositoriesViewLabelProvider_UnbornBranchText,
						StyledString.QUALIFIER_STYLER);
