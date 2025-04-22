			Rectangle r = getRectangle(root);
			if (r == null) {
				return;
			}
			List<MUIElement> visibleChildren = SashUtil
					.getVisibleChildren(sr.container);
			double availableRelative = SashUtil.getAvailableRelative(
					sr.container.isHorizontal(),
					sr.container.isHorizontal() ? r.width : r.height,
					sashWidth, visibleChildren);
			double totalRelative = SashUtil.getTotalWeight(visibleChildren);

			if (newLeft < minSize) {
				newLeft = minSize;
			}
			newRight = totalSize - sashWidth - newLeft;
			if (newRight < minSize) {
				newRight = minSize;
			}
