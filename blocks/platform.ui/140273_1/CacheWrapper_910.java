			Control child = children[0];
			Rectangle newBounds = composite.getClientArea();
			if (!newBounds.equals(lastBounds)) {
				child.setBounds(newBounds);
				lastBounds = newBounds;
			}
