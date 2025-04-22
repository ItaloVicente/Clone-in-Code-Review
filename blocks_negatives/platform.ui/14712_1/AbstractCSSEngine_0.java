			InputStream stream = url.openStream();
			InputSource tempStream = new InputSource();
			tempStream.setURI(url.toString());
			tempStream.setByteStream(stream);
			parseImport = true;
			styleSheet = (CSSStyleSheet) this.parseStyleSheet(tempStream);
			parseImport = false;
