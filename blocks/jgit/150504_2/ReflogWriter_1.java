		Repository repo = refdb.getRepository();
		CoreConfig.LogRefUpdates value = repo.isBare()
				? CoreConfig.LogRefUpdates.FALSE
				: CoreConfig.LogRefUpdates.TRUE;
		value = repo.getConfig().getEnum(ConfigConstants.CONFIG_CORE_SECTION
				null
		if (value != null) {
			switch (value) {
			case FALSE:
				break;
			case TRUE:
				return refName.equals(HEAD) || refName.startsWith(R_HEADS)
						|| refName.startsWith(R_REMOTES)
						|| refName.startsWith(R_NOTES);
			case ALWAYS:
				return refName.equals(HEAD) || refName.startsWith(R_REFS);
			default:
				break;
			}
		}
		return false;
