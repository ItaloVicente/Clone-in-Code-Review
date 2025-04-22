					ImageData xdata = URLImageDescriptor.getImageData(xUrl);
					if (xdata != null) {
						return xdata;
					}
				}
				String xpath = FileImageDescriptor.getxPath(url, zoom);
				if (xpath != null) {
					URL xPathUrl = getURL(xpath);
					if (xPathUrl != null) {
						return URLImageDescriptor.getImageData(xPathUrl);
					}
