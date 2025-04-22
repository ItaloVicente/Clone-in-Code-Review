				if (id.fRGBs.length == fRGBs.length) {
					boolean result = id.fLength == fLength;
					for (int i = 0; i < fRGBs.length && result; i++) {
						result = result && id.fRGBs[i].equals(fRGBs[i]);
					}
					return result;
				}
