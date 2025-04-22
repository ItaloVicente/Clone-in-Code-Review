			Collections.sort(branches, new Comparator<Ref>() {

				public int compare(Ref o1, Ref o2) {
					String refName1 = o1.getName();
					String refName2 = o2.getName();
					if (refName1.startsWith(Constants.R_REMOTES)) {
						if (refName2.startsWith(Constants.R_HEADS))
							return -1;
						else
							return refName1.compareTo(refName2);
					} else {
						if (refName2.startsWith(Constants.R_REMOTES))
							return 1;
						else
							return refName1.compareTo(refName2);
					}
				}
			});
			Ref branch = branches.get(0).getLeaf();
