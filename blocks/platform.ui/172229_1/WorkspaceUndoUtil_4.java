				}
			} else {
				IPath parentPath = destination;
				if (pathIncludesName) {
					parentPath = destination.removeLastSegments(1);
				}
				IContainer generatedParent = generateContainers(parentPath);
				if ((createLinks || createVirtual)
						&& (source.isLinked() == false)) {
					if (source.getType() == IResource.FILE) {
						IFile file = workspaceRoot.getFile(destinationPath);
						file.createLink(createRelativePath(
								source.getLocationURI(), relativeToVariable, file), 0,
								iterationProgress.split(100));
