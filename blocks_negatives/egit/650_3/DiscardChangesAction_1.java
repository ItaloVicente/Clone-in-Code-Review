	/**
	 * @param res an IResource
	 * @return An ArrayList with all members of this IResource
	 * of arbitrary depth. This will return just the argument
	 * res if it is a file.
	 */
	private ArrayList<IResource> getAllMembers(IResource res) {
		ArrayList<IResource> ret = new ArrayList<IResource>();
		if (res.getLocation().toFile().isFile()) {
			ret.add(res);
		} else {
			getAllMembersHelper(res, ret);
		}
		return ret;
	}


	private void getAllMembersHelper(IResource res, ArrayList<IResource> ret) {
		ArrayList<IResource> tmp = new ArrayList<IResource> ();
		if (res instanceof IContainer) {
			IContainer cont = (IContainer) res;
			try {
				for (IResource r : cont.members()) {
					if (r.getLocation().toFile().isFile()) {
						tmp.add(r);
					} else {
						getAllMembersHelper(r, tmp);
					}
				}
			} catch (CoreException e) {
				return;
			}

			ret.addAll(tmp);
		}
	}

