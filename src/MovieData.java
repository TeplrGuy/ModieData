import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieData implements MovieDataInterface {
	List<Movie> MovieList = new ArrayList<Movie>();
	ArrayList<Director> DirectorList = new ArrayList<Director>();
	private String apiKey = "bbb0e77b94b09193e6f32d5fac7a3b9c";

	@Override
	public Connection GetConnection() {
		final String driverClass = "org.sqlite.JDBC";
		final String url = "jdbc:sqlite:src/SQLite/Movie.db";
		try {
			// Load the JDBC drivers
			Class.forName(driverClass);
			// Open a DB Connection
			// connection = DriverManager.getConnection(url, username, password);
			Connection connection = DriverManager.getConnection(url);
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}

	@Override
	public void GetData(String key) throws IOException {
		// TODO Auto-generated method stub
		URL url = null;

		try {
			url = new URL("https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=" + key
					+ "&region=GR&year=2018");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");

		BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			try {
				ParseMovieData(output);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	@Override
	public void GetCredits(String id) throws IOException {
		// TODO Auto-generated method stub
		URL url = null;

		try {
			url = new URL("https://api.themoviedb.org/3/movie/" + id + "/credits?api_key=" + apiKey);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");

		BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

		String output;
		while ((output = br.readLine()) != null) {

			try {
				ParseCreditsData(output);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
	}

	@Override
	public void ParseCreditsData(String output) {
		final JSONObject obj = new JSONObject(output);
		final JSONArray geodata = obj.getJSONArray("crew");
		final int n = geodata.length();
		for (int i = 0; i < n; ++i) {
			final JSONObject movieCredits = geodata.getJSONObject(i);
			Director newDirector = new Director();
			if (movieCredits.getString("department").equals("Directing")) {
				// System.out.print(movieCredits.getInt("id") +","
				// +movieCredits.getString("name"));
				newDirector.setId(movieCredits.getInt("id"));
				newDirector.setName(movieCredits.getString("name"));
				newDirector.setLink("https://www.themoviedb.org/person/" + movieCredits.getInt("id"));
				DirectorList.add(newDirector);
				AddDirector(newDirector.getName(), newDirector.getLink(), newDirector.getId());
			}
		}
	}

	@Override
	public void ParseMovieData(String output) {
		final JSONObject obj = new JSONObject(output);
		final JSONArray geodata = obj.getJSONArray("results");
		final int n = geodata.length();

		for (int i = 0; i < n; ++i) {
			final JSONObject movie = geodata.getJSONObject(i);
			Movie newMovie = new Movie();
			ArrayList<String> DirectorListt = new ArrayList<String>();

			newMovie.setTitle(movie.getString("title"));
			newMovie.setOriginalTitle(movie.getString("original_title"));
			newMovie.setDescribtion(movie.getString("overview"));
			newMovie.setId(movie.getInt("id"));

			try {
				GetCredits(Integer.toString(movie.getInt("id")));
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			newMovie.setListofDirectors(DirectorList);
			MovieList.add(newMovie);
			for (int ii = 0; ii < newMovie.getListofDirectors().size(); ii++)
				DirectorListt.add(Integer.toString(newMovie.getListofDirectors().get(ii).getId()));

			AddMovie(newMovie.getTitle(), newMovie.getDescribtion(), newMovie.getOriginalTitle(),
					DirectorListt.toString(), newMovie.getLink(), newMovie.getId(), i);

			System.out.println();
			System.out.println();

			DirectorList.clear();
			DirectorListt.clear();
		}

	}

	@Override
	public void AddMovie(String Title, String Description, String OriginalTitle, String DirectorList, String Link,
			int MovieId, int Index) {
		// TODO Auto-generated method stub
		String insertUpdate = "INSERT OR UPDATE INTO movie(title, description, original_title, directors, movie_id) VALUES(?, ?, ?,?,?)";
		Connection conn = null;

		try {

			conn = GetConnection();
			PreparedStatement pstmt = conn.prepareStatement(insertUpdate);
			pstmt.setString(1, Title);
			pstmt.setString(2, Description);
			pstmt.setString(3, OriginalTitle);
			pstmt.setString(4, DirectorList);
			pstmt.setInt(5, MovieId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void AddDirector(String Name, String Link, int DirectorId) {
		// TODO Auto-generated method stub
		String insertUpdate = "INSERT OR UPDATE INTO director(director_id, name, link) VALUES(?, ?, ?)";
		Connection conn = null;
		int count = 0;
		try {
			count = count + 1;
			conn = GetConnection();
			PreparedStatement pstmt = conn.prepareStatement(insertUpdate);
			pstmt.setInt(1, DirectorId);
			pstmt.setString(2, Name);
			pstmt.setString(3, Link);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
