		}
		size.x = Math.max(minSize.width, size.x);
		size.y = Math.max(minSize.height, size.y);
		if (!size.equals(oldSize)) {
			commentAndDiffComposite.setSize(size);
			commentAndDiffComposite.layout();
		}
