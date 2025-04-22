			if(gitPath == null || gitPath.isEmpty()) {
				walk.setTreeFilter(TreeFilter.ANY_DIFF);
			} else {
				walk.setTreeFilter(AndTreeFilter.create(PathFilterGroup
						.createFromStrings(Collections.singleton(gitPath)),
						TreeFilter.ANY_DIFF));
			}
