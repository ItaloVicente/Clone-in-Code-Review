			if (getGpgSignature() != null) {
					throw new JGitInternalException(
							JGitText.get().signedTagMessageNoLf);
				}
				String signature = getGpgSignature().toExternalString();
				w.write(signature);
				w.flush();
					os.write('\n');
				}
			}
