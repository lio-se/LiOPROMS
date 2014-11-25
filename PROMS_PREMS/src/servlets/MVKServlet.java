package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MVKServlet
 */
public class MVKServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String mvk = "http://dev.apigw.minavardkontakter.se/infrastructure/supportservices/forminteraction/v1/forms";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String requestvalue[] = request.getParameterValues("do");
		if (requestvalue != null) {

			String charset = "UTF-8";
			
			/*
			 * Creating a form from a template_id
			 */
			if (requestvalue[0].equals("createForm")) {
				String healthcareFacilityCareUnit = "apigw-dev"; // test clinic
				// String templateid = "215edffa-538e-4508-b294-c46f69233c96";
				// String templateversion ="3";

				String query = String
						.format("<urn:createForm xmlns:urn=\"urn:org:apigw:infrastructure:supportservices:forminteraction:MimeTypes:1\">"
								+ "<urn:healthcare_facility_CareUnit>%s</urn:healthcare_facility_CareUnit>"
								+ "<urn:templateId>%s</urn:templateId>"
								+ "<urn:templateVersion>%s</urn:templateVersion>"
								+ "</urn:createForm>",
								URLEncoder.encode(healthcareFacilityCareUnit,
										charset),
								URLEncoder.encode(request
										.getParameterValues("templateID")[0],
										charset),
								URLEncoder.encode(
										request.getParameterValues("templateVersion")[0],
										charset));
				System.out.println(query);
				URL url = new URL(mvk);

				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Authorization",
						"Bearer cf70be6c-9dcd-11e1-8c70-12313c036113");
				connection.setDoOutput(true);
				connection.setRequestProperty("Accept-Charset", charset);
				connection
						.setRequestProperty(
								"Content-Type",
								"application/vnd-riv.infrastructure.supportservices.forminteraction.CreateForm.v1+xml");

				try {
					OutputStream output = connection.getOutputStream();
					output.write(query.getBytes(charset));
					output.close();
				} catch (Exception e) {
				}

				int responsecode = connection.getResponseCode();
				System.out.println(connection.getResponseCode() + ": "
						+ connection.getResponseMessage());
				Map<String, List<String>> map = connection.getHeaderFields();
				for (Map.Entry<String, List<String>> entry : map.entrySet()) {
					System.out.println("Key : " + entry.getKey()
							+ " , Value : " + entry.getValue());
				}
				try {
					InputStream is = connection.getInputStream();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is));
					String inputLine = null;

					while ((inputLine = br.readLine()) != null) {
						if (inputLine.trim().length() == 0) {
							continue;
						} else {
							System.out.println(inputLine);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					RequestDispatcher errorview = request
							.getRequestDispatcher("html/error.html");
					errorview.include(request, response);

				} finally {
					if (connection != null)
						connection.disconnect();
				}

				OutputStream out = response.getOutputStream();
				if (responsecode == 201) {
					out.write("Formulär skapat".getBytes(charset));
				} else {
					out.write(String.format("Åtgärden misslyckades: %s",
							connection.getResponseMessage()).getBytes());
				}
			}
			/*
			 * Fetching a form for a form_id
			 */
			else if (requestvalue[0].equals("getForm")) {
				String form_id = request.getParameterValues("formID")[0];
				URL url = new URL(mvk + "/" + form_id);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestProperty("Authorization",
						"Bearer cf70be6c-9dcd-11e1-8c70-12313c036113");
				connection
						.setRequestProperty(
								"Content-Type",
								"application/vnd-riv.infrastructure.supportservices.forminteraction.Form.v1+xml");
				int responsecode1 = connection.getResponseCode();
				System.out.println(connection.getResponseCode() + ": "
						+ connection.getResponseMessage());
				Map<String, List<String>> map1 = connection.getHeaderFields();
				for (Map.Entry<String, List<String>> entry : map1.entrySet()) {
					System.out.println("Key : " + entry.getKey()
							+ " , Value : " + entry.getValue());
				}
				try {
					InputStream is = connection.getInputStream();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is));
					String inputLine = null;

					OutputStream out = response.getOutputStream();
					StringBuffer buffer = new StringBuffer();

					while ((inputLine = br.readLine()) != null) {
						if (inputLine.trim().length() == 0) {
							continue;
						} else {
							System.out.println(inputLine);
							buffer.append((inputLine + "\n"));
						}
					}
					out.write(buffer.toString().getBytes());

				} catch (Exception e) {
					e.printStackTrace();
					RequestDispatcher headerview = request
							.getRequestDispatcher("html/error.html");
					headerview.include(request, response);

				} finally {
					if (connection != null)
						connection.disconnect();
				}
			}
			/*
			 * Submitting a form 
			 */
			else if (requestvalue[0].equals("answerForm")){
				String form_id = request.getParameterValues("formID")[0];
				String form_page = request.getParameterValues("formPage")[0];
				
				URL url = new URL(mvk + "/" + form_id);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestProperty("Authorization",
						"Bearer cf70be6c-9dcd-11e1-8c70-12313c036113");
				connection
						.setRequestProperty(
								"Content-Type",
								"application/vnd-riv.infrastructure.supportservices.forminteraction.Form.v1+xml");
				int responsecode1 = connection.getResponseCode();
				System.out.println(connection.getResponseCode() + ": "
						+ connection.getResponseMessage());
				Map<String, List<String>> map1 = connection.getHeaderFields();
				for (Map.Entry<String, List<String>> entry : map1.entrySet()) {
					System.out.println("Key : " + entry.getKey()
							+ " , Value : " + entry.getValue());
				}
				try {
					InputStream is = connection.getInputStream();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is));
					String inputLine = null;

					OutputStream out = response.getOutputStream();
					StringBuffer buffer = new StringBuffer();

					while ((inputLine = br.readLine()) != null) {
						if (inputLine.trim().length() == 0) {
							continue;
						} else {
							System.out.println(inputLine);
							buffer.append((inputLine + "\n"));
						}
					}
					out.write(buffer.toString().getBytes());

				} catch (Exception e) {
					e.printStackTrace();
					RequestDispatcher headerview = request
							.getRequestDispatcher("html/error.html");
					headerview.include(request, response);

				} finally {
					if (connection != null)
						connection.disconnect();
				}
			}

		} else {
			System.out.println("error no request parameters."
					+ request.getParameterMap());
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
