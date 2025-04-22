					refName.append(ref.getLeaf().getName(),
							StyledString.QUALIFIER_STYLER);
					refName.append(" - ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
					refName.append(ref.getLeaf().getObjectId().name(),
							StyledString.QUALIFIER_STYLER);
				} else {
					refName.append(" - ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
					refName.append(ref.getObjectId().name(),
							StyledString.QUALIFIER_STYLER);

