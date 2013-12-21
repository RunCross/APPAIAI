package TEST;

public class Message {
	private String Text = "";
	private String Link = "";

	public Message(String text, String link) {
		this.Text = text;
		this.Link = link;
	}

	public String getText() {
		return Text;
	}

	public String getLink() {
		return Link;
	}

	public void setText(String text) {
		this.Text = text;

	}

	public void setLink(String link) {
		this.Link = link;
	}
}
