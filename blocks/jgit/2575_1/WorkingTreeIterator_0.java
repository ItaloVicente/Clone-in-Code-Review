			String p = repository.getConfig().getString(
					ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_EXCLUDESFILE);
			File excludesfile;
			if (p.startsWith("~/"))
				excludesfile = new File(repository.getFS().userHome()
						p.substring(2));
			else
				excludesfile = new File(p);
			loadRulesFromFile(r

