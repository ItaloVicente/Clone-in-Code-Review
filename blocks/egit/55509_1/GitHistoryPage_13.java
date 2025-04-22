				IHyperlinkDetector[] registered = getRegisteredHyperlinkDetectors(
						sourceViewer);
				IHyperlinkDetector[] result = new IHyperlinkDetector[registered.length
						+ 1];
				System.arraycopy(registered, 0, result, 0, registered.length);
				result[registered.length] = new CommitMessageViewer.KnownHyperlinksDetector();
				return result;
			}
