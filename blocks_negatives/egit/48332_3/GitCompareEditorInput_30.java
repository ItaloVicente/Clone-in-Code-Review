				baseCommit = rw.parseCommit(repository.resolve(baseVersion));
			} catch (IOException e) {
				throw new InvocationTargetException(e);
			}

			final RevCommit compareCommit;
			if (compareVersion == null)
				compareCommit = null;
			else
