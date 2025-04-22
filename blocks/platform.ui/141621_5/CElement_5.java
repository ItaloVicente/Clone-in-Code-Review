				for (IResource member : members) {
					if (member instanceof IProject) {
						_children.add(member);
					} else if (member instanceof IFolder) {
						_children.add(new CContainer(_cp, member, this));
					} else {
						_children.add(member);
					}
