			if (null != property) switch (property) {
		    	case IS_INITIALIZED:
			    return gitFlowRepository.getConfig().isInitialized();
		    	case IS_FEATURE:
			    return gitFlowRepository.isFeature();
		    	case IS_RELEASE:
			    return gitFlowRepository.isRelease();
		    	case IS_HOTFIX:
			    return gitFlowRepository.isHotfix();
		    	case IS_DEVELOP:
			    return gitFlowRepository.isDevelop();
		    	case IS_MASTER:
			    return gitFlowRepository.isMaster();
		    	case HAS_DEFAULT_REMOTE:
			    return gitFlowRepository.getConfig().hasDefaultRemote();
		    	default:
			    break;
		    }
