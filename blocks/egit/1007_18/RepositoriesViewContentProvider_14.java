				if (uriCount == 1)
					children.add(new PushNode(node, node.getRepository(),
							firstUri.toPrivateString()));
				else
					children.add(new PushNode(node, node.getRepository(),
							firstUri.toPrivateString() + "...")); //$NON-NLS-1$
			}
