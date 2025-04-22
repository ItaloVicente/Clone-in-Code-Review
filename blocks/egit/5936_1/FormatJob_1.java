			synchronized(lock) {
				SWTCommit commit = (SWTCommit)formatRequest.getCommit();
				commit.parseBody();
				builder = new CommitInfoBuilder(formatRequest.getRepository(), commit,
						formatRequest.getCurrentDiffs(), formatRequest.isFill(), formatRequest.getAllRefs());
				builder.setColors(formatRequest.getLinkColor(),
						formatRequest.getDarkGrey(),
						formatRequest.getHunkheaderColor(),
						formatRequest.getLinesAddedColor(),
						formatRequest.getLinesRemovedColor());
			}
