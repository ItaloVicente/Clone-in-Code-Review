			} else {
				String signingKey = gpgConfig.getSigningKey();
				if (!StringUtils.isEmptyOrNull(signingKey)) {
					CLabel warning = new CLabel(left, SWT.WRAP);
					warning.setText(MessageFormat.format(
							UIText.CreateTagDialog_noSigningKey, signingKey));
					warning.setToolTipText(
							UIText.CreateTagDialog_noSigningKeyToolTip);
					warning.setImage(PlatformUI.getWorkbench().getSharedImages()
							.getImage(ISharedImages.IMG_OBJS_WARN_TSK));
				}
