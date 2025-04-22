	private void scrollCommentAndDiff(Point location, int lineHeight) {
		Rectangle size = commentAndDiffScrolledComposite.getBounds();
		ScrollBar bar = commentAndDiffScrolledComposite.getVerticalBar();
		if (bar != null && bar.isVisible()) {
			size.width = Math.max(0, size.width - bar.getSize().x);
		}
		bar = commentAndDiffScrolledComposite.getHorizontalBar();
		if (bar != null && bar.isVisible()) {
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
			int minScroll = size.width / 4;
			if (location.x < left) {
				left = Math.max(0,
						left - Math.max(left - location.x, minScroll));
			} else if (location.x > left + size.width) {
				int maxWidth = commentAndDiffComposite.getSize().x;
				int right = Math.max(location.x, left + size.width + minScroll);
				left = Math.min(right, maxWidth) - size.width;
			}
			if (location.y < top) {
				top = location.y;
			} else if (location.y > top + size.height) {
				top = location.y - size.height;
			}
			commentAndDiffScrolledComposite.setOrigin(left, top);
		}
	}

