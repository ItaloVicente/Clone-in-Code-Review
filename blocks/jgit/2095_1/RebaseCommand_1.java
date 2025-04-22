					rup.setNewObjectId(newHead);
					Result res = rup.forceUpdate();
					switch (res) {
					case FAST_FORWARD:
					case FORCED:
					case NO_CHANGE:
						break;
					default:
						throw new JGitInternalException("Updating HEAD failed");
