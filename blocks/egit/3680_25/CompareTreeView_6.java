				IResource childResource = (IResource) child;
				if (childResource.isLinked())
					continue;
				IPath path = new Path(
						repositoryMapping.getRepoRelativePath(childResource));
				boolean isFile = childResource.getType() == IResource.FILE;
