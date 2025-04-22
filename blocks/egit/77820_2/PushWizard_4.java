		}
		final Job job = new PushJob(
				NLS.bind(UIText.PushWizard_jobName,
						getURIsString(operation.getSpecification().getURIs())),
				localDb, operation, resultToCompare,
				getDestinationString(repoPage.getSelection()), true);
