		} catch (CorruptObjectException e ) {
			getServletContext().log(MessageFormat.format(
					HttpServerText.get().receivedCorruptObject
					e.getMessage()
					ServletUtils.identify(rp.getRepository())));
			consumeRequestBody(req);
			out.close();

