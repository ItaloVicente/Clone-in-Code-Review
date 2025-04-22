			} catch (ServiceMayNotContinueException noPack) {
				throw noPack;
			} catch (IOException err) {
				if (reportInternalServerErrorOverSideband())
					throw new UploadPackInternalServerErrorException(err);
				else
					throw err;
			} catch (RuntimeException err) {
				if (reportInternalServerErrorOverSideband())
