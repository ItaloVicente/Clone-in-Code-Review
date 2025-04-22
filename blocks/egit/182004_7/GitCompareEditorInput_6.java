		}
		Repository repo = getRepository();
		File myDir = repo == null ? null : repo.getDirectory();
		repo = other.getRepository();
		File otherDir = repo == null ? null : repo.getDirectory();
		return Objects.equals(myDir, otherDir);
