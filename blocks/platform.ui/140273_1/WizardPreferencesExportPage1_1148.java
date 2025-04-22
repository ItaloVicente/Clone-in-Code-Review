				int pathSepIndex = destinationText.lastIndexOf(File.separator);
				if (pathSepIndex != -1 && dotIndex < pathSepIndex) {
					destinationText += idealSuffix;
				}
			} else {
				destinationText += idealSuffix;
			}
		}
