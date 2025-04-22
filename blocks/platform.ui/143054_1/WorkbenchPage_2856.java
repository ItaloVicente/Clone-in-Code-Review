			List<IWorkingSet> newList = new ArrayList<>(Arrays.asList(workingSets));
			if (newList.remove(event.getOldValue())) {
				setWorkingSets(newList.toArray(new IWorkingSet[newList.size()]));
			}
		}
	};
