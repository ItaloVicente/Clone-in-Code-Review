			ObjectReader reader = repo.newObjectReader();
			try {
				breakModifies(reader
				findExactRenames(pm);
				findContentRenames(reader
				rejoinModifies(pm);
			} finally {
				reader.release();
			}
