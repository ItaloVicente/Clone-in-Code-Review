		synchronized(lock) {
			builder = new CommitInfoBuilder(formatRequest.getRepository(), formatRequest.getCommit(),
					formatRequest.getCurrentDiffs(), formatRequest.isFill(), formatRequest.getAllRefs());
			builder.setColors(formatRequest.getLinkColor(),
					formatRequest.getDarkGrey(),
					formatRequest.getHunkheaderColor(),
					formatRequest.getLinesAddedColor(),
					formatRequest.getLinesRemovedColor());
		}
