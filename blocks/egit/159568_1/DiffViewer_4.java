			RevCommit base = d.getBase();
			if (base == null) {
				base = d.getCommit().getParent(0);
			}
			openInEditor(d.getRepository(), d.getOldPath(), base, blobs[0],
					lineNoToReveal);
