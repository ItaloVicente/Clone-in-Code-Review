			Rectangle r = getRectangle(root);
			List<MUIElement> visibleChildren = SashUtil
					.getVisibleChildren(sr.container);
			double availableRelative = SashUtil.getAvailableRelative(
					sr.container.isHorizontal(),
					sr.container.isHorizontal() ? r.width : r.height,
					sashWidth, visibleChildren);
			double totalRelative = SashUtil.getTotalWeight(visibleChildren);

			double minLeft = infoLeft.getMinValueAsAbsolute(totalRelative,
					availableRelative);
			if (minLeft < minSize) {
				minLeft = minSize;
			}
			double maxLeft = infoLeft.getMaxValueAsAbsolute(totalRelative,
					availableRelative);
			double minRight = infoRight.getMinValueAsAbsolute(totalRelative,
					availableRelative);
			if (minRight < minSize) {
				minRight = minSize;
			}
			double maxRight = infoRight.getMaxValueAsAbsolute(totalRelative,
					availableRelative);

			if (newLeft < minLeft) {
				newLeft = minLeft;
			} else if (newLeft > maxLeft) {
				newLeft = maxLeft;
			}
			newRight = totalSize - sashWidth - newLeft;
			if (newRight < minRight) {
				newRight = minRight;
				newLeft = totalSize - sashWidth - newRight;
			} else if (newRight > maxRight) {
				newRight = maxRight;
				newLeft = totalSize - sashWidth - newRight;
			}
