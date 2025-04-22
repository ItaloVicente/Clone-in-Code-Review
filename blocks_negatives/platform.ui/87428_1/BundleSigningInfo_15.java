						Date signDate = signedContent
								.getSigningTime(signers[0]);
						if (signDate != null)
							dateText = DateFormat.getDateTimeInstance().format(
									signDate);
						else
							dateText = WorkbenchMessages.BundleSigningTray_Unknown;
					}
