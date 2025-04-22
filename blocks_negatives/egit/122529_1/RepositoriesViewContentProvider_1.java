			if (node.getRepository().isBare())
				return false;
			File workingDir = repo.getWorkTree();
			if (!workingDir.exists())
				return false;
			return workingDir.listFiles().length > 0;
