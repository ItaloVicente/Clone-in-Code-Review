		Repository repo = mapping.getRepository();
		if(repo == null){
			return null;
		}

		if (repo.isBare()) {
			return new IndexDiffData();
		}
		
