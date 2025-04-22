				Set<String> proposals = new TreeSet<>(
						String.CASE_INSENSITIVE_ORDER);

				try {
					Set<String> remotes = repository.getRefDatabase()
							.getRefs(Constants.R_REMOTES).keySet();
					for (String remote : remotes) {
						int slashIndex = remote.indexOf('/');
						if (slashIndex > 0 && slashIndex < remote.length() - 1)
							proposals
									.add(remote.substring(remote.indexOf('/') + 1));
					}
				} catch (IOException e) {
				}

				for (final String proposal : proposals) {
