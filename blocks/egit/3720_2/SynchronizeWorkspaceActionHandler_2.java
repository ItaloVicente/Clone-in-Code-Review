				GitSynchronizeData data = new GitSynchronizeData(entry.getKey(), HEAD, HEAD, true);
				Set<IContainer> containers = entry.getValue();
				if (!containers.isEmpty())
					data.setIncludedPaths(containers);

				gsdSet.add(data);
