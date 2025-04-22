			computedSize.y = Math.max(computedSize.y, currentSize.y);
			if (computedSize.equals(currentSize)) {
				return;
			}
			setShellSize(computedSize.x, computedSize.y);
			lastShellSize = getShell().getSize();
