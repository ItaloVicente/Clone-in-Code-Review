			if (overwriteState == OVERWRITE_NONE) {
				return;
			}

			if (overwriteState != OVERWRITE_ALL) {
				String overwriteAnswer = overwriteCallback
						.queryOverwrite(properPathString);
