
		X509Certificate[] certs = (X509Certificate[]) req
				.getAttribute("javax.servlet.request.X509Certificate");
		if (certs != null && certs.length > 0) {
			String name = certs[0].getSubjectDN().getName();
			Matcher m = Pattern.compile("CN=([^
			if (m.matches()) {
				user = m.group(1);
				return createFor(req
			}
		}

