
			if (aggregateWorkingSetId == null) {
				aggregateWorkingSet = findAggregateWorkingSet(workingSetManager);
				aggregateWorkingSetId = aggregateWorkingSet == null ? getDefaultAggregateWorkingSetId()
						: aggregateWorkingSet.getName();
			} else {
				aggregateWorkingSet = (AggregateWorkingSet) workingSetManager
						.getWorkingSet(aggregateWorkingSetId);
			}

