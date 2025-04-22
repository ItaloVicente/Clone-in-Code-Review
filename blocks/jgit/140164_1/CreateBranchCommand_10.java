			if (null
					== upstreamMode) {
                            String autosetupflag = repo.getConfig().getString(
                                    ConfigConstants.CONFIG_BRANCH_SECTION
                                    ConfigConstants.CONFIG_KEY_AUTOSETUPMERGE);
                            if (null == autosetupflag) {
                                doConfigure = baseBranch.startsWith(Constants.R_REMOTES);
                            } else switch (autosetupflag) {
                                case "false":
                                    doConfigure = false;
                                    break;
                                case "always":
                                    doConfigure = true;
                                    break;
                                default:
