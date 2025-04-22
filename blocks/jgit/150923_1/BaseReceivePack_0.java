					if (advertisedHaves.contains(cmd.getNewId())) {
						immediateRefs.add(cmd.getNewId());
					}
				} else if (cmd.getType() == ReceiveCommand.Type.CREATE) {
					if (advertisedHaves.contains(cmd.getNewId())) {
						immediateRefs.add(cmd.getNewId());
					} else {
						newRefs.add(cmd.getNewId());
					}
