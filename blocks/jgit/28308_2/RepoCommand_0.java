			try {
				return readFileFromRepo(repo
			} finally {
				FileUtils.delete(dir
			}
		}

		protected byte[] readFileFromRepo(Repository repo
				String ref
