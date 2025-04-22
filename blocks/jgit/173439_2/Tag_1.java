					if (noSign) {
						command.setSigned(false);
					} else if (sign) {
						command.setSigned(true);
					}
					if (annotated) {
						command.setAnnotated(true);
					} else if (message == null && !sign && gpgKeyId == null) {
						command.setAnnotated(false);
					}
					command.setSigningKey(gpgKeyId);
