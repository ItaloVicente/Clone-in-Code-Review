		return MessageFormat
				.format(
						MarkerMessages.marker_statusSummarySelected,
						new Object[] { entries.length,
								MessageFormat
										.format(
												MarkerMessages.errorsAndWarningsSummaryBreakdown,
												counts[0], counts[1], /* combine infos and others */ counts[2] + counts[3])});
