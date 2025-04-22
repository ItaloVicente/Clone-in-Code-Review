				d.accept(delta -> {
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
