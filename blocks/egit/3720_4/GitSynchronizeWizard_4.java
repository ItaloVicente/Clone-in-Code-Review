				Repository repo = branchesEntry.getKey();
				GitSynchronizeData data = new GitSynchronizeData(
						repo, HEAD, branchesEntry.getValue(),
						shouldIncludeLocal);
				Set<IContainer> containers = getSelectedContainers(repo);
				if (containers != null)
					data.setIncludedPaths(containers);
				gsdSet.add(data);
