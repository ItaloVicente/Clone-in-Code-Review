		case SUBMODULES:
			List<RepositoryNode> children = new ArrayList<RepositoryNode>();
			try {
				SubmoduleWalk walk = SubmoduleWalk.forIndex(node
						.getRepository());
				while (walk.next()) {
					Repository subRepo = walk.getRepository();
					if (subRepo != null)
						children.add(new RepositoryNode(node, subRepo));
				}
			} catch (IOException e) {
				handleException(e, node);
			}
			return children.toArray();
