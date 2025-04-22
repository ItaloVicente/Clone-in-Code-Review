			else {
				IPath[] locations = getSelectedLocations(event);
				if (locations.length > 0)
					CompareUtils.compare(locations[0], repository,
							GitFileRevision.INDEX, Constants.HEAD, false,
							workBenchPage);
			}
		} catch (IOException e) {
			Activator.handleError(
					UIText.CompareWithRefAction_errorOnSynchronize, e, true);
