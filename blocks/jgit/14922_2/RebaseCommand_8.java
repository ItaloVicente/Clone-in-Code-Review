
		public static Step create(AbbreviatedObjectId commitId
				byte[] shortMessage
			Step theStep = new Step(action);
			theStep.commit = commitId;
			theStep.shortMessage = shortMessage;
			return theStep;
		}
