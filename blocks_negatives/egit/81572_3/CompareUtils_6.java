		 * Using a local context for the ResourceMapping computation would make
		 * for a faster test... but we need the model providers to be able to
		 * load remote information. The local file may very well be a single
		 * file, but it is possible that the remote side has multiple files to
		 * take into account for that model. (if part of the logical model has
		 * been locally deleted, or if some new files have been created on the
		 * remote side(s).)
