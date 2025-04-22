			FileRepository existingRepository;
			try {
				existingRepository = new FileRepository(repositoryPath);
			} catch (IOException e) {
				return false;
			}
