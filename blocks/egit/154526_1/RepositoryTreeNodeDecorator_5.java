			Ref ref = refCache.exact(node.getRepository(),
					node.getObject().getName());
			if (ref == null) {
				return;
			}
			RevCommit latest = getLatestCommit(node.getRepository(), ref);
