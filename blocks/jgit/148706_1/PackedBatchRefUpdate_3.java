				new RefList.Builder<>(refs.size() + delta);
		int refIdx =0;
		int cmdIdx = 0;
		while (refIdx < refs.size() || cmdIdx < commands.size()) {
			Ref ref = (refIdx < refs.size()) ? refs.get(refIdx) : null;
			ReceiveCommand cmd = (cmdIdx < commands.size()) ?commands.get(cmdIdx) : null;
			int cmp = 0;
			if (ref != null && cmd != null) {
				cmp = ref.getName().compareTo(cmd.getRefName());
			}  else if (ref == null) {
				cmp = 1;
			} else if (cmd == null) {
				cmp = -1;
