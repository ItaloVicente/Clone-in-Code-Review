	private static final class RefHoverInformationControlCreator extends
			AbstractReusableInformationControlCreator {
		@Override
		protected IInformationControl doCreateInformationControl(Shell parent) {
			return new DefaultInformationControl(parent);
		}
	}

	private final class RefHoverInformationControlManager extends
			AbstractHoverInformationControlManager {

		protected RefHoverInformationControlManager() {
			super(new RefHoverInformationControlCreator());
		}

		@Override
		protected void computeInformation() {
			MouseEvent e = getHoverEvent();

			TableItem item = table.getTable().getItem(new Point(e.x, e.y));
			if (item != null) {
				SWTCommit commit = (SWTCommit) item.getData();
				if (commit != null && commit.getRefCount() > 0) {
					Rectangle itemBounds = item.getBounds();
					int firstColumnWidth = table.getTable().getColumn(0).getWidth();
					int relativeX = e.x - firstColumnWidth - itemBounds.x;
					for (int i = 0; i < commit.getRefCount(); i++) {
						Ref ref = commit.getRef(i);
						Point textSpan = renderer.getRefHSpan(ref);
						if ((textSpan != null)
								&& (relativeX >= textSpan.x && relativeX <= textSpan.y)) {

							String hoverText = getHoverText(ref, i, commit);
							int width = textSpan.y - textSpan.x;
							Rectangle rectangle = new Rectangle(
									firstColumnWidth + itemBounds.x
											+ textSpan.x, itemBounds.y, width,
									itemBounds.height);
							setInformation(hoverText, rectangle);
							return;
						}
					}
				}
			}

			setInformation(null, null);
		}

		private String getHoverText(Ref ref, int refIndex, SWTCommit commit) {
			if (ref.getName().startsWith(Constants.R_TAGS)
					&& renderer.isShownAsEllipsis(ref)) {
				StringBuilder sb = new StringBuilder(UIText.CommitGraphTable_HoverAdditionalTags);
				for (int i = refIndex; i < commit.getRefCount(); i++) {
					Ref tag = commit.getRef(i);
					String name = tag.getName();
					if (name.startsWith(Constants.R_TAGS)) {
						sb.append('\n');
						sb.append(name.substring(Constants.R_TAGS.length()));
					}
				}
				return sb.toString();
			} else {
				return getHoverTextForSingleRef(ref);
			}
		}

		private String getHoverTextForSingleRef(Ref r) {
			StringBuilder sb = new StringBuilder();
			String name = r.getName();
			sb.append(name);
			if (r.isSymbolic()) {
				sb.append(r.getLeaf().getName());
			}
			String description = GitLabels.getRefDescription(r);
			if (description != null) {
				sb.append(description);
			}
			return sb.toString();
		}
	}

