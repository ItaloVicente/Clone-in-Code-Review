					refName.append(" - ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
					refName.append(ObjectId.toString(ref.getLeaf()
							.getObjectId()), StyledString.QUALIFIER_STYLER);
				} else {
					refName.append(" - ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
					refName.append(ObjectId.toString(ref.getObjectId()),
							StyledString.QUALIFIER_STYLER);

				}
