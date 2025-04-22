			String readWritePath = path.append(FN_DIALOG_SETTINGS).toOSString();
			dialogSettings.save(readWritePath);
		} catch (IOException e) {
		} catch (IllegalStateException e) {
		}
	}

	@Deprecated
