				String urlString = fullUrl.toString();
				if (urlString.contains("/loop/")) {
					urlString = urlString.replace("/loop/"
					if (urlString.contains("/loop/x/x/x/x/x/x/x/x/")) {
						urlString = urlString.replace("/loop/x/x/x/x/x/x/x/x/"
								"/loop/");
					}
					httpServletResponse.setStatus(
							HttpServletResponse.SC_MOVED_TEMPORARILY);
					httpServletResponse.setHeader(HttpSupport.HDR_LOCATION
							urlString);
					return;
				}
				int responseCode = HttpServletResponse.SC_MOVED_PERMANENTLY;
				int nofRedirects = 0;
				Matcher matcher = responsePattern.matcher(urlString);
				if (matcher.find()) {
					nofRedirects = Integer
							.parseUnsignedInt(matcher.group(1));
					responseCode = Integer.parseUnsignedInt(matcher.group(2));
					if (--nofRedirects <= 0) {
						urlString = fullUrl.substring(0
								+ fullUrl.substring(matcher.end());
					} else {
						urlString = fullUrl.substring(0
								+ "/response/" + nofRedirects + "/"
								+ responseCode + '/'
								+ fullUrl.substring(matcher.end());
					}
				}
				httpServletResponse.setStatus(responseCode);
				if (nofRedirects <= 0) {
					urlString = urlString.replace("/redirect"
				}
