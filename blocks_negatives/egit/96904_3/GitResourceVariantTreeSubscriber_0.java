			for (IResource member : ((IContainer) res).members())
				allMembers.put(member.getName(), member);

			for (GitSyncObjectCache gitMember : gitCachedMembers) {
				IResource member = allMembers.get(gitMember.getName());
				if (member != null)
					gitMembers.add(member);
