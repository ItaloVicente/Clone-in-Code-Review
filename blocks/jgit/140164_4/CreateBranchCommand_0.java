			if (null == upstreamMode) {
				doConfigure = defaultcase(baseBranch);
			} else
				switch (upstreamMode) {
				case SET_UPSTREAM:
				case TRACK:
					doConfigure = true;
					break;
				case NOTRACK:
					doConfigure = false;
					break;
				default:
					doConfigure = defaultcase(baseBranch);
					break;
				}
