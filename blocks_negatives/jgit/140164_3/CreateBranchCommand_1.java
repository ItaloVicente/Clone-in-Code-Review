                                    doConfigure = baseBranch.startsWith(Constants.R_REMOTES);
                                } else switch (autosetupflag) {
                                case "false":
                                    doConfigure = false;
                                    break;
                                case "always":
                                    doConfigure = true;
                                    break;
                                default:
                                    doConfigure = baseBranch.startsWith(Constants.R_REMOTES);
                                    break;
                            }
			}
