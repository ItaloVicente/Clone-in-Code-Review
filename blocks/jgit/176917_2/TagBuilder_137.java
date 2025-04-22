			GpgSignature signature = getGpgSignature();
			if (signature != null) {
					throw new JGitInternalException(
							JGitText.get().signedTagMessageNoLf);
				}
				String externalForm = signature.toExternalString();
				w.write(externalForm);
				w.flush();
					os.write('\n');
				}
			}
