				boolean isCurrentBranch = false;
				if (currentBranch != null) {
					Ref ref = repositories[0].getRefDatabase()
							.getRefs(Constants.R_HEADS).get(shortName);
					isCurrentBranch = ref != null
							? currentBranch.equals(ref.getName()) : false;
				}
				createMenuItem(menu, repositories, isCurrentBranch, shortName);
