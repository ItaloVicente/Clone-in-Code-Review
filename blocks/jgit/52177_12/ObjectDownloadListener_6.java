package org.eclipse.jgit.lfs.server;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_SERVICE_UNAVAILABLE;

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

	protected LargeFileRepository getLargeFileRepository() {
		return repository;
	}

	@Override
	protected void doPost(HttpServletRequest req
			throws ServletException
		res.setStatus(SC_OK);
		res.setContentType(CONTENTTYPE_VND_GIT_LFS_JSON);

		Writer w = new BufferedWriter(
				new OutputStreamWriter(res.getOutputStream()

		GsonBuilder gb = new GsonBuilder()
				.setFieldNamingPolicy(
						FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.setPrettyPrinting().disableHtmlEscaping();

		Gson gson = gb.create();
		Reader r = new BufferedReader(new InputStreamReader(req.getInputStream()
		LfsRequest request = gson.fromJson(r

		LargeFileRepository repo = getLargeFileRepository();
		if (repo == null) {
			res.setStatus(SC_SERVICE_UNAVAILABLE);
			return;
		}

		TransferHandler handler = TransferHandler
				.forOperation(request.operation
		gson.toJson(handler.process()
		w.flush();
	}
}
