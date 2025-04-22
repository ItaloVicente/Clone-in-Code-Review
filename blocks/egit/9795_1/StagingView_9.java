				boolean addCompareWithOurs = availableActions
						.contains(StagingEntry.Action.COMPARE_WITH_OURS);
				boolean addCompareWithTheirs = availableActions
						.contains(StagingEntry.Action.COMPARE_WITH_THEIRS);

				if (addCompareWithOurs)
					menuMgr.add(createItem(
							ActionCommands.COMPARE_WITH_OURS_ACTION,
							tableViewer));
				if (addCompareWithTheirs)
					menuMgr.add(createItem(
							ActionCommands.COMPARE_WITH_THEIRS_ACTION,
							tableViewer));
