		Map<String
		if (pc.matching != null) {
			byRef = new HashMap<>();
			for (ReceiveCommand cmd : pc.matching) {
				if (byRef.put(cmd.getRefName()
					throw new IllegalStateException();
				}
			}
		} else {
			byRef = null;
		}

