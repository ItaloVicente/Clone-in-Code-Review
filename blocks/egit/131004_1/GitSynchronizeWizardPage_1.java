				List<String> refNames = new LinkedList<>();
				List<Ref> refs;
				try {
					refs = repo.getRefDatabase()
							.getRefsByPrefix(RefDatabase.ALL);
				} catch (IOException e) {
					refs = Collections.emptyList();
				}
				for (Ref ref : refs) {
					refNames.add(ref.getName());
				}
