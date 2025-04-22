
		OutputStream out = rsp.getOutputStream();
		if (compressStream && acceptsGzipEncoding(req)) {
			rsp.setHeader(HDR_CONTENT_ENCODING
			out = new GZIPOutputStream(out);
		}
		return out;
