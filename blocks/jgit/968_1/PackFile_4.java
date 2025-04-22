		} catch (DataFormatException dataFormat) {
			setCorrupt(src.copyOffset);

			CorruptObjectException corruptObject = new CorruptObjectException(
					MessageFormat.format(
							JGitText.get().objectAtHasBadZlibStream
							src.copyOffset
			corruptObject.initCause(dataFormat);

			StoredObjectRepresentationNotAvailableException gone;
			gone = new StoredObjectRepresentationNotAvailableException(src);
			gone.initCause(corruptObject);
			throw gone;

		} catch (IOException ioError) {
			StoredObjectRepresentationNotAvailableException gone;
			gone = new StoredObjectRepresentationNotAvailableException(src);
			gone.initCause(ioError);
			throw gone;
