		} catch (FileTypeValidationException.EmptyExtensionWithNoNameException e) {
			setErrorMessage(WorkbenchMessages.FileExtension_extensionEmptyMessage);
			return false;
		} catch (FileTypeValidationException.PatternIsSingleWildCharException e) {
			setErrorMessage(WorkbenchMessages.FileExtension_extensionEmptyMessage);
			return false;
		} catch (FileTypeValidationException.IllegalWildCharPositionException e) {
			setErrorMessage(WorkbenchMessages.FileExtension_fileNameInvalidMessage);
			return false;
		} catch (FileTypeValidationException.MultipleWildCharsException e) {
			setErrorMessage(WorkbenchMessages.FileExtension_fileNameInvalidMessage);
			return false;
		} catch (FileTypeValidationException e) {
			return false;
		}
