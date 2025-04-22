				myData.getId()), (IJobFunction) monitor -> {
					try {
						if (myData != data)
							return Status.OK_STATUS;
						SignedContent signedContent = contentFactory
								.getSignedContent(myData.getBundle());
						if (myData != data)
							return Status.OK_STATUS;
						SignerInfo[] signers = signedContent.getSignerInfos();
						final String signerText, dateText;
						if (!isOpen() && BundleSigningInfo.this.data == myData)
							return Status.OK_STATUS;
