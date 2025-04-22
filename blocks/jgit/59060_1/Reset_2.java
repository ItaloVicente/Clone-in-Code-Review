			if (paths.size() > 0) {
				for (String path : paths)
					command.addPath(path);
			} else {
				ResetType mode = null;
				if (soft)
					mode = selectMode(mode
				if (mixed)
					mode = selectMode(mode
				if (hard)
					mode = selectMode(mode
				if (mode == null)
					throw die("no reset mode set");
				command.setMode(mode);
			}
