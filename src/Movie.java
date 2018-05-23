import java.util.ArrayList;

public class Movie {
	private int Id;
	private String Title;
	private String Describtion;
	private String OriginalTitle;
	private ArrayList<Director> ListofDirectors;
	private String Link;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescribtion() {
		return Describtion;
	}
	public void setDescribtion(String describtion) {
		Describtion = describtion;
	}
	public String getOriginalTitle() {
		return OriginalTitle;
	}
	public void setOriginalTitle(String originalTitle) {
		OriginalTitle = originalTitle;
	}
	public ArrayList<Director> getListofDirectors() {
		return ListofDirectors;
	}
	public void setListofDirectors(ArrayList<Director> listofDirectors) {
		ListofDirectors = listofDirectors;
	}
	public String getLink() {
		return Link;
	}
	public void setLink(String link) {
		Link = link;
	}
	
	
	

}
