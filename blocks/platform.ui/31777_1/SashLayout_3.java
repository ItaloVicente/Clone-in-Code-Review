			int newSize;
			Matcher matcher = patternAny.matcher(subNode.getContainerData());
			if (matcher.matches() && "px".equals(matcher.group(2))) { //$NON-NLS-1$
				newSize = Integer.parseInt(matcher.group(1));
			} else {
				double ratio = getWeight(subNode) / totalWeight;
				newSize = (int) ((availableWidth * ratio) + 0.5);
			}
