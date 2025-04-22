			ResetType mode = null;
			if (soft)
				mode = selectMode(mode, ResetType.SOFT);
			if (mixed)
				mode = selectMode(mode, ResetType.MIXED);
			if (hard)
				mode = selectMode(mode, ResetType.HARD);
			if (mode == null)
				throw die("no reset mode set");
			command.setMode(mode);
