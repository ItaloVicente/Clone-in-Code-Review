		initContentProvider(target -> {
			if (target == input)
				return children;
			if (target == input2)
				return children2;
			return null;
