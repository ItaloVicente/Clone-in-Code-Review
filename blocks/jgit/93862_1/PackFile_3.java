		} catch (FileNotFoundException fn) {
			openFail(!packFile.exists());
			throw fn;
		} catch (EOFException | AccessDeniedException | NoSuchFileException
				| CorruptObjectException | NoPackSignatureException
				| PackMismatchException | UnpackException
				| UnsupportedPackIndexVersionException
				| UnsupportedPackVersionException pe) {
