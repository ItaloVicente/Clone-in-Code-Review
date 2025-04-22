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
						Point textSpan = renderer.getRefHSpan(commit.getRef(i));
						if ((textSpan != null)
								&& (relativeX >= textSpan.x && relativeX <= textSpan.y)) {

							String hoverText = getHoverText(commit.getRef(i));
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

		private String getHoverText(Ref r) {
			String name = r.getName();
			if (r.isSymbolic())
				name += ": " + r.getLeaf().getName(); //$NON-NLS-1$
			return name;
		}
	}

