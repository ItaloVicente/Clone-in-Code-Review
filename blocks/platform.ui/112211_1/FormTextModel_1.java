			if (wrapper == null) {
				rawText = originalText;
			} else {
				String encoding = doc.getXmlEncoding();
				if (encoding != null) {
					try {
						encoding = Charset.forName(encoding).name();
					} catch (IllegalCharsetNameException e) {
						encoding = null;
					} catch (UnsupportedCharsetException e) {
						encoding = null;
					}
				}
				if (encoding == null) {
					encoding = "UTF-8"; //$NON-NLS-1$
				}
				rawText = wrapper.toString(encoding);
			}
