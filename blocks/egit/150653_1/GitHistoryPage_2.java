	private void scrollCommentAndDiff(Point location, int lineHeight) {
		Rectangle size = commentAndDiffScrolledComposite.getBounds();
		ScrollBar bar = commentAndDiffScrolledComposite.getVerticalBar();
		if (bar != null) {
			size.width = Math.max(0, size.width - bar.getSize().x);
		}
		bar = commentAndDiffScrolledComposite.getHorizontalBar();
		if (bar != null) {
			size.height = Math.max(0, size.height - bar.getSize().y);
		}
		Point topLeft = commentAndDiffScrolledComposite.getOrigin();
		size.x = topLeft.x;
		size.y = topLeft.y;
		if (location.y >= size.y) {
			location.y += lineHeight;
		}
		if (!size.contains(location)) {
			int left = size.x;
			int top = size.y;
			if (location.x < left) {
				left = location.x;
			} else if (location.x > left + size.width) {
				left = Math.max(0, location.x - size.width);
			}
			if (location.y < top) {
				top = location.y;
			} else if (location.y > top + size.height) {
				top = Math.max(0, location.y - size.height);
			}
			commentAndDiffScrolledComposite.setOrigin(left, top);
		}
	}

