				setContentDescription(NLS
						.bind(
								UIText.CompareTreeView_ComparingTwoVersionDescription,
								new String[] {
										leftVersion,
										name,
										rightVersion.equals(INDEX_VERSION) ? UIText.CompareTreeView_IndexVersionText
												: rightVersion }));
