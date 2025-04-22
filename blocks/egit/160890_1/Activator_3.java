				} catch (IOException e) {
					logError(
							MessageFormat.format(
									CoreText.Activator_ignoreResourceFailed,
									r.getFullPath()), e);
				}
				return false;
}
return true;
});
