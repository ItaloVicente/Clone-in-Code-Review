			@Override
			protected Action getAction() {
				if (expectedResult == null || !expectedResult.equals(result)) {
					return new ShowPushResultAction(repo, result,
							destinationString, showConfigureButton);
				}
				return null;
			}

