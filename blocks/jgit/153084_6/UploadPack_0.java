			V0PackSender sender = new V0PackSender(req
					(refs == null && req.getClientCapabilities()
							.contains(OPTION_INCLUDE_TAG)) ? emptyList()
									: refs.values()
					unshallowCommits);
			sender.sendPack(accumulator);
