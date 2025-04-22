
        boolean doRebase = false;
        switch (pullRebaseMode) {
            case REBASE:
                doRebase = true;
                break;
            case NO_REBASE:
                doRebase = false;
                break;
            case USE_CONFIG:
            default:
                doRebase = repoConfig.getBoolean(
                        ConfigConstants.CONFIG_BRANCH_SECTION
                        ConfigConstants.CONFIG_KEY_REBASE
                break;
        }
