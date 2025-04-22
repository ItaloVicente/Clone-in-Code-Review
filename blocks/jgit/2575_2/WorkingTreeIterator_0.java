			FS fs = repository.getFS();
			String path = repository.getConfig().get(CoreConfig.KEY)
					.getExcludesFile();
			if (path != null) {
				File excludesfile;
				if (path.startsWith("~/"))
					excludesfile = fs.resolve(fs.userHome()
				else
					excludesfile = fs.resolve(null
				loadRulesFromFile(r
			}

			File exclude = fs
					.resolve(repository.getDirectory()
			loadRulesFromFile(r

			return r.getRules().isEmpty() ? null : r;
		}

		private void loadRulesFromFile(IgnoreNode r
				throws FileNotFoundException
