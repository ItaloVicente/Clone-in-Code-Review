				if (modeO == 0) {
					if (isWorktreeDirty(work, ourDce))
						return false;
					if (nonTree(modeT)) {
						if (e != null) {
							addToCheckout(tw.getPathString(), e, attributes);
