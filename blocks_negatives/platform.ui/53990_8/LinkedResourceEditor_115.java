				fProject.accept(new IResourceVisitor() {
					/**
					 * @throws CoreException
					 */
					@Override
					public boolean visit(IResource resource)
							throws CoreException {
						if (resource.isLinked() && !resource.isVirtual())
							resources.add(resource);
						return true;
					}
				});
