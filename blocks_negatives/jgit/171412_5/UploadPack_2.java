			sendPack(accumulator,
					req,
					req.getClientCapabilities().contains(OPTION_INCLUDE_TAG)
						? db.getRefDatabase().getRefsByPrefix(R_TAGS)
						: null,
					unshallowCommits, deepenNots, pckOut);
