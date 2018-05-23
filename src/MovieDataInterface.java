import java.io.IOException;
import java.sql.Connection;

public interface MovieDataInterface {

	// GetConnection method establishes connection to our sql database
	Connection GetConnection() throws IOException;
	
	// GetData method gets data via api call from the movie db
	// using a string the user inputs
	void GetData(String SearchKey) throws IOException;
	
	// GetCredits method gets data via api call from the movie db
	// using a movie id
	void GetCredits(String MovieID) throws IOException;
	 
	// ParseMovieData method parses json response from GetData method
	void ParseMovieData(String Key);
	
	// ParseCreditsData method parses json response from GetCredits method
	void ParseCreditsData(String Output);
	
	// AddMovie method Inserts movie into database - movie table 
	// and updates if data already exits
	void AddMovie(String Title, String Description, String OriginalTitle, String DirectorList, String Link, int Key, int Index);
	
	// AddDirector method Inserts director into database - director table 
	// and updates if data already exits
	void AddDirector(String Name, String Link, int DirectorId);
}
