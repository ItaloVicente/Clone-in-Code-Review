				d.accept(new IResourceDeltaVisitor() {

					@Override
					public boolean visit(IResourceDelta delta)
							throws CoreException {
						if ((delta.getKind() & (IResourceDelta.ADDED | IResourceDelta.CHANGED)) == 0)
							return false;
						int flags = delta.getFlags();
						if ((flags != 0)
								&& ((flags & IResourceDelta.DERIVED_CHANGED) == 0))
							return false;

						final IResource r = delta.getResource();
						if ((r.getProject() != null)
								&& (RepositoryMapping.getMapping(r) == null))
							return false;
						if (r.isTeamPrivateMember())
							return false;

						if (r.isDerived()) {
							try {
								IPath location = r.getLocation();
								if (RepositoryUtil.canBeAutoIgnored(location)) {
									toBeIgnored.add(location);
								}
							} catch (IOException e) {
								logError(
										MessageFormat.format(
												CoreText.Activator_ignoreResourceFailed,
												r.getFullPath()), e);
							}
							return false;
						}
						return true;
