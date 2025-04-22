						AboutBundleData.ExtendedSigningInfo info = data.getInfo();
						Bundle bundle = data.getBundle();
						if (info != null && info.isSigned(bundle)) {
							signerText = info.getSigningDetails(bundle);
							dateText = DateFormat.getDateTimeInstance().format(info.getSigningTime(bundle));
							signingTypeText = info.getSigningType(bundle);
						} else {
							signerText = WorkbenchMessages.BundleSigningTray_Unsigned;
							dateText = WorkbenchMessages.BundleSigningTray_Unsigned;
							signingTypeText = WorkbenchMessages.BundleSigningTray_Unsigned;
						}
