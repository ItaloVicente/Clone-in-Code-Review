
package org.eclipse.jgit.lfs.server;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.servlet.http.HttpServletResponse.SC_OK;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lfs.lib.LargeFileRepository;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LfsProtocolServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static class LfsRequest {
		String operation;
		List<LfsObject> objects;
	}

	private final LargeFileRepository repository;

	public LfsProtocolServlet(LargeFileRepository repository) {
		this.repository = repository;
	}

	protected LfsProtocolServlet() {
	  this.repository = null;
	}

	@Override
	protected void doPost(HttpServletRequest req
			throws ServletException
		res.setStatus(SC_OK);
		res.setContentType("application/vnd.git-lfs+json");

		Writer w = new BufferedWriter(
				new OutputStreamWriter(res.getOutputStream()

		GsonBuilder gb = new GsonBuilder()
				.setFieldNamingPolicy(
						FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.setPrettyPrinting();

		Gson gson = gb.create();
		Reader r = new BufferedReader(new InputStreamReader(req.getInputStream()
		LfsRequest request = gson.fromJson(r

		Response.Body body;
		if ("upload".equals(request.operation)
				|| "download".equals(request.operation)) {
			body = new TransferHandler(getLargeFileRepository()
			    request.objects).process();
		} else {
			throw new UnsupportedOperationException(
					request.operation + " not supported");
		}

		gson.toJson(body
		w.flush();
	}

	protected LargeFileRepository getLargeFileRepository() {
	  return repository;
	}
}
