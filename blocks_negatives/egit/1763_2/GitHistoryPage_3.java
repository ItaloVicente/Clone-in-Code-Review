			if (showAllFilter == ShowFilter.SHOWALLFOLDER) {
				final String name = map.getRepoRelativePath(r.getParent());
				if (name != null && name.length() > 0)
					paths.add(name);
			} else if (showAllFilter == ShowFilter.SHOWALLPROJECT) {
				final String name = map.getRepoRelativePath(r.getProject());
				if (name != null && name.length() > 0)
					paths.add(name);
			} else if (showAllFilter == ShowFilter.SHOWALLREPO) {
			} else /* if (showAllFilter == ShowFilter.SHOWALLRESOURCE) */{
				final String name = map.getRepoRelativePath(r);
				if (name != null && name.length() > 0)
					paths.add(name);
