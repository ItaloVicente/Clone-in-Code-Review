			} else if (o instanceof Path) {
				Path path = (Path) o;
				RepositoryMapping mapping = RepositoryMapping.getMapping(path);
				if (mapping != null) {
					repo = mapping.getRepository();
					input = new HistoryPageInput(repo,
							new File[] { path.toFile() });
				}
