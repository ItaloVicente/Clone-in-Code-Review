							testFor), e);
					return e.getMessage();
				} catch (RevisionSyntaxException e) {
					String m = MessageFormat.format(
							UIText.ValidationUtils_InvalidRevision,
							testFor);
					Activator.logError(m, e);
					return m;
