			if (ref != null && ref.isSymbolic())
				realName = ref.getTarget().getName();
			else
				realName = rev;
			String remote = repo.getConfig().getString(CONFIG_BRANCH_SECTION,
					name, CONFIG_KEY_REMOTE);
			String merge = repo.getConfig().getString(CONFIG_BRANCH_SECTION,
					name, CONFIG_KEY_MERGE);

			return new RemoteConfig(remote, merge);
