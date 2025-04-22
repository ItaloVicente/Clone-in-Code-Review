				for (RefSpec pushSpec : remoteConfig.getPushRefSpecs()) {
					String destination = pushSpec.getDestination();
					if (destination == null)
						continue;
					if (destination.startsWith(GerritUtil.REFS_FOR))
						return true;
				}
				for (RefSpec fetchSpec : remoteConfig.getFetchRefSpecs()) {
					String source = fetchSpec.getSource();
					String destination = fetchSpec.getDestination();
					if (source == null || destination == null)
						continue;
					if (source.startsWith(Constants.R_NOTES)
							&& destination.startsWith(Constants.R_NOTES))
						return true;
