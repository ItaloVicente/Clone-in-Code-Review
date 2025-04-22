				for (int i = 0; i < members.length; i++) {
					if (members[i] instanceof IProject)
						_children.add(members[i]);
					else if (members[i] instanceof IFolder)
						_children.add(new CContainer(_cp, members[i], this));
					else
						_children.add(members[i]);
