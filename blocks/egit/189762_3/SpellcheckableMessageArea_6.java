				if (useCommentHighlight) {
					DefaultDamagerRepairer commentRepairer = new DefaultDamagerRepairer(
							new HyperlinkTokenScanner(this, viewer,
									commentColoring));
					reconciler.setDamager(commentRepairer,
							COMMENT_CONTENT_TYPE);
					reconciler.setRepairer(commentRepairer,
							COMMENT_CONTENT_TYPE);
				}
