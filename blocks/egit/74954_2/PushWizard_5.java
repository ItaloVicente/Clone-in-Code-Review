
		@Override
		protected Action getAction() {
			Repository repo = localDb;
			if (repo != null && (resultToCompare == null
					|| !resultToCompare.equals(operationResult))) {
				return new ShowPushResultAction(repo, operationResult,
						destinationString, true);
			}
			return null;
		}

		@Override
		public boolean belongsTo(Object family) {
			if (JobFamilies.PUSH.equals(family)) {
				return true;
			}
			return super.belongsTo(family);
		}

