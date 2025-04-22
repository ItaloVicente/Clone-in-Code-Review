			} else if (transferConfig.isAllowRefInWant() &&
				String refName = line.substring(OPTION_WANT_REF.length() + 1);
				Ref ref = db.getRefDatabase().exactRef(refName);
				if (ref == null) {
					throw new PackProtocolException(
							MessageFormat.format(JGitText.get().invalidRefName
								refName));
				}
				ObjectId oid = ref.getObjectId();
				if (oid == null) {
					throw new PackProtocolException(
							MessageFormat.format(JGitText.get().invalidRefName
								refName));
				}
				wantedRefs.put(refName
				wantIds.add(oid);
