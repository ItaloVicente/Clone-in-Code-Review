				} else if (updates.size() > 1) {
					List<String> allLocalNames = updates.stream()
							.map(RemoteRefUpdate::getSrcRef)
							.collect(Collectors.toList());
					if (!warnMultiple(parent, allLocalNames)) {
						return null;
					}
