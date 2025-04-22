			IContainer container = (IContainer) res;
			if (container.exists()) {
				for (IResource member : container.members()) {
					existingMembers.put(member.getName(), member);
				}
			}
