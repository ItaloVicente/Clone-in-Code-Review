				String refName = line.substring(OPTION_WANT_REF.length() + 1);
				Ref ref = refdb.exactRef(refName);
				if (ref == null) {
					throw new PackProtocolException(MessageFormat
							.format(JGitText.get().invalidRefName, refName));
				}
				ObjectId oid = ref.getObjectId();
				if (oid == null) {
					throw new PackProtocolException(MessageFormat
							.format(JGitText.get().invalidRefName, refName));
				}
				reqBuilder.addWantedRef(refName, oid);
				reqBuilder.addWantsIds(oid);
