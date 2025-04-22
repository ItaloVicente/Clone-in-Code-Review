		public void handleSetChange(SetChangeEvent event) {
			Set addedKeys = new HashSet(event.diff.getAdditions());
			Set removedKeys = new HashSet(event.diff.getRemovals());
			Map oldValues = new HashMap();
			Map newValues = new HashMap();
			for (Iterator it = removedKeys.iterator(); it.hasNext();) {
				Object removedKey = it.next();
				Object oldValue = null;
