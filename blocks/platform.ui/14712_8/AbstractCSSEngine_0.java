			InputStream stream = null;
			try {
				stream = url.openStream();
				InputSource tempStream = new InputSource();
				tempStream.setURI(url.toString());
				tempStream.setByteStream(stream);
				parseImport = true;
				styleSheet = (CSSStyleSheet) this.parseStyleSheet(tempStream);
				parseImport = false;
				CSSRuleList tempRules = styleSheet.getCssRules();
				for (int j = 0; j < tempRules.getLength(); j++) {
					masterList.add(tempRules.item(j));
				}
			} finally {
				if (stream != null) {
					stream.close();
				}
