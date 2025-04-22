					if (myData != data)
						return Status.OK_STATUS;
					SignedContent signedContent = myData.getSignedContent();
					if (signedContent == null) {
						StatusManager.getManager().handle(new Status(IStatus.WARNING, WorkbenchPlugin.PI_WORKBENCH,
								WorkbenchMessages.BundleSigningTray_Cant_Find_Service), StatusManager.LOG);
						return Status.OK_STATUS;
					}
					if (myData != data)
						return Status.OK_STATUS;
					SignerInfo[] signers = signedContent.getSignerInfos();
					final String signerText, dateText;
					if (!isOpen() && BundleSigningInfo.this.data == myData)
						return Status.OK_STATUS;

					if (signers.length == 0) {
						signerText = WorkbenchMessages.BundleSigningTray_Unsigned;
						dateText = WorkbenchMessages.BundleSigningTray_Unsigned;
					} else {
						Properties[] certs = parseCerts(signers[0].getCertificateChain());
						if (certs.length == 0)
							signerText = WorkbenchMessages.BundleSigningTray_Unknown;
						else {
							StringBuilder buffer = new StringBuilder();
							for (Iterator<Entry<Object, Object>> i = certs[0].entrySet().iterator(); i.hasNext();) {
								Entry<Object, Object> entry = i.next();
								buffer.append(entry.getKey());
								buffer.append('=');
								buffer.append(entry.getValue());
								if (i.hasNext())
									buffer.append('\n');
