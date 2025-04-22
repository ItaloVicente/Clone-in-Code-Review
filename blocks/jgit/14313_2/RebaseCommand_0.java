
		writeSteps("# Created by EGit: rebasing " + upstreamCommit.name()
				+ " onto " + headId.name()
				cherryPickList
					private ObjectReader reader = walk.getObjectReader();

					public String getToken(RevCommit obj) {
						return Action.PICK.toToken();
					}

					public String getName(RevCommit obj) throws IOException {
						return reader.abbreviate(obj).name();
					}

					public String getShortMessage(RevCommit obj) {
						return obj.getShortMessage();
					}

				});
