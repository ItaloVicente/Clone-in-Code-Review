		HashSet<IProgressUpdateCollector> newCollectors = new HashSet<>();
        for (int i = 0; i < collectors.length; i++) {
            if (!collectors[i].equals(provider)) {
				newCollectors.add(collectors[i]);
			}
        }
        IProgressUpdateCollector[] newArray = new IProgressUpdateCollector[newCollectors
                .size()];
        newCollectors.toArray(newArray);
        collectors = newArray;
