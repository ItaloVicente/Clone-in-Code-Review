				int compare = o1.getRepositoryName().compareToIgnoreCase(
						o2.getRepositoryName());
				if (compare == 0)
					compare = o1.getRevCommit().name()
							.compareTo(o2.getRevCommit().name());
				return compare;
