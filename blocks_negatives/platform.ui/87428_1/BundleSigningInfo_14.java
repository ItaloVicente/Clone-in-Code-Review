					if (signers.length == 0) {
						signerText = WorkbenchMessages.BundleSigningTray_Unsigned;
						dateText = WorkbenchMessages.BundleSigningTray_Unsigned;
					} else {
						Properties[] certs = parseCerts(signers[0]
								.getCertificateChain());
						if (certs.length == 0)
							signerText = WorkbenchMessages.BundleSigningTray_Unknown;
						else {
							StringBuffer buffer = new StringBuffer();
							for (Iterator i = certs[0].entrySet().iterator(); i
									.hasNext();) {
								Map.Entry entry = (Entry) i.next();
								buffer.append(entry.getKey());
								buffer.append('=');
								buffer.append(entry.getValue());
								if (i.hasNext())
									buffer.append('\n');
