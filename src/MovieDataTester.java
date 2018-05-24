import java.io.IOException;
import java.util.Scanner;

public class MovieDataTester {

	static MovieData md = new MovieData();

	private static Scanner in;
	private static int userInput;

	private static void options() {
		System.out.println("Kindly follow the options below!");
		System.out.println("1. Search For a Movie");
		System.out.println("2. Display Options");
		System.out.println("3. To Quit");
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String search = "love";
		md.GetConnection();
		// System.out.println(md.MovieList.get(0).getListofDirectors().get(0).getLink());

		in = new Scanner(System.in);
		options();
		while (userInput != 8) {
			System.out.print("Main Menu: Enter a number (Enter 3 to see all options): ");
			String temp = in.next();
			try {
				userInput = Integer.parseInt(temp);
			} catch (Exception e) {
				userInput = 0;
			}
			try {
				switch (userInput) {
				case 1:
					// Search movie
					System.out.println("Search (Example: Spider Man): ");
					search = in.next();
					md.GetData(search);
					md.MovieList.clear();

					break;
				case 2:
					// Display options again
					System.out.println();
					options();
					break;
				case 3:
					// Exit
					System.out.println("Exiting...");
					System.exit(0);
					break;
				default:
					System.out.println("Invalid choice. Make sure you enter a number between 1-7");
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
		}
	}

}
