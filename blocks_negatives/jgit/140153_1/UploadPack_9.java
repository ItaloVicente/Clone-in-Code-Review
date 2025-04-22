			} catch (IOException err) {
				if (reportInternalServerErrorOverSideband())
					throw new UploadPackInternalServerErrorException(err);
				else
					throw err;
			} catch (RuntimeException err) {
				if (reportInternalServerErrorOverSideband())
					throw new UploadPackInternalServerErrorException(err);
				else
					throw err;
			} catch (Error err) {
