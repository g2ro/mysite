package mysite.vo;

public class SiteVo {
	private Long id;
	private String title;
	private String welcom;
	private String profile;
	private String description;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWelcom() {
		return welcom;
	}
	public void setWelcom(String welcom) {
		this.welcom = welcom;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "SiteVo [id=" + id + ", title=" + title + ", welcom=" + welcom + ", profile=" + profile
				+ ", description=" + description + "]";
	}
	
	
	
}
