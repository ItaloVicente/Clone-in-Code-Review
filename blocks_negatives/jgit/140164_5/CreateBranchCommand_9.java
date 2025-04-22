			if (upstreamMode == SetupUpstreamMode.SET_UPSTREAM
					|| upstreamMode == SetupUpstreamMode.TRACK)
				doConfigure = true;
			else if (upstreamMode == SetupUpstreamMode.NOTRACK)
				doConfigure = false;
			else {
				String autosetupflag = repo.getConfig().getString(
						ConfigConstants.CONFIG_BRANCH_SECTION, null,
						ConfigConstants.CONFIG_KEY_AUTOSETUPMERGE);
				if (null == autosetupflag) {
