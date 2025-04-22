				IResourceDelta delta = event.getDelta();

				boolean projectPreDelete = false;
				if (event.getType() == IResourceChangeEvent.PRE_DELETE
						&& event.getResource().getType() == IResource.PROJECT) {
					projectPreDelete = true;
				}
				if ((delta.getKind() == IResourceDelta.CHANGED
						&& (delta.getFlags() & INTERESTING_CHANGES) == 0)
						|| !projectPreDelete) {
					return;
				}

