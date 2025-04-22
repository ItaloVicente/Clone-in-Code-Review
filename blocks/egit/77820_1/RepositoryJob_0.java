				IStatus finalStatus = getDeferredStatus();
				String msg = finalStatus.getMessage();
				if (msg == null || msg.isEmpty()) {
					return new Status(finalStatus.getSeverity(),
							finalStatus.getPlugin(), finalStatus.getCode(),
							action.getText(), finalStatus.getException());
				}
				return finalStatus;
