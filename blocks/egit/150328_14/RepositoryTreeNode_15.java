			}
		}
		if (myType == RepositoryTreeNodeType.REPOGROUP
				&& other.myType == RepositoryTreeNodeType.REPOGROUP) {
			RepositoryGroup myGroup = ((RepositoryGroupNode)this).getGroup();
			RepositoryGroup otherGroup = ((RepositoryGroupNode)other).getGroup();
			return myGroup.getGroupId().equals(otherGroup.getGroupId())
					&& myGroup.getName().equals(otherGroup.getName());
		}
