		public void handleMapChange(MapChangeEvent event) {
			MapDiff diff = event.diff;
			Set rangeSetAdditions = new HashSet();
			Set rangeSetRemovals = new HashSet();
			final Set adds = new HashSet();
			final Set changes = new HashSet();
			final Set removes = new HashSet();
			final Map oldValues = new HashMap();

			for (Iterator it = diff.getAddedKeys().iterator(); it.hasNext();) {
				Object addedKey = it.next();
				Object newValue = diff.getNewValue(addedKey);
