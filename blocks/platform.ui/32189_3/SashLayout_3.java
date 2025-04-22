			if (infoLeft.isDefaultAbsolute()) {
				infoLeft.setDefaultValue(newLeft);
			} else {
				if (absoluteOnResize) {
					infoLeft.setDefaultValue(newLeft);
					infoLeft.setDefaultAbsolute(true);
				} else {
					double newWeight = newLeft * totalRelative
							/ (availableRelative);
					infoLeft.setDefaultValue((newWeight));
				}
			}
			sr.left.setContainerData(infoLeft.getEncodedParameters());

			if (infoRight.isDefaultAbsolute()) {
				infoRight.setDefaultValue(newRight);
			} else {
				if (absoluteOnResize) {
					infoRight.setDefaultValue(newRight);
					infoRight.setDefaultAbsolute(true);
				} else {
					double newWeight = newRight * totalRelative
							/ (availableRelative);
					infoRight.setDefaultValue((newWeight));
				}
			}
			sr.right.setContainerData(infoRight.getEncodedParameters());
		}
