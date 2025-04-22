				pm.beginTask("", 2); //$NON-NLS-1$

				pm.subTask(MessageFormat.format(
						CoreText.SquashCommitsOperation_squashing,
						Integer.valueOf(commits.size())));

