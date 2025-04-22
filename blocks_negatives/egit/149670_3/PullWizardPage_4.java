				}, uri -> {
					AsynchronousBranchList list = refs.get(uri);
					if (list == null) {
						list = new AsynchronousBranchList(repository, uri,
								null);
						refs.put(uri, list);
					}
					return list;
				});
