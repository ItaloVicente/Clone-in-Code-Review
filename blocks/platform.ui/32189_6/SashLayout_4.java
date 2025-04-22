			if (infoLeft.getResizeMode() == PartResizeMode.WEIGHTED) {
				infoLeft.setDefaultAbsolute(false);
				double newWeight = newLeft * totalRelative / availableRelative;
				infoLeft.setDefaultValue(newWeight);
			} else {
				infoLeft.setDefaultAbsolute(true);
				infoLeft.setDefaultValue(newLeft);
			}
			sr.left.setContainerData(infoLeft.getEncodedParameters());

			if (infoRight.getResizeMode() == PartResizeMode.WEIGHTED) {
				infoRight.setDefaultAbsolute(false);
				double newWeight = newRight * totalRelative / availableRelative;
				infoRight.setDefaultValue(newWeight);
			} else {
				infoRight.setDefaultAbsolute(true);
				infoRight.setDefaultValue(newRight);
			}
			sr.right.setContainerData(infoRight.getEncodedParameters());
		}
