				Map<String
						Constants.R_HEADS);
				Map<String
						.getRefs(Constants.R_REMOTES);
				for (Iterator<Entry<String
						.entrySet().iterator(); iterator
						.hasNext();) {
					Entry<String
							.next();
					for (String remoteKey : remotesRefList.keySet()) {
						if (remoteKey.endsWith(entry.getKey()))
							refSpecs.add(new RefSpec(entry.getValue().getLeaf()
								.getName()));
					}
				}
