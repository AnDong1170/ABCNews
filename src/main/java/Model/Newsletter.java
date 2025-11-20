package Model;

public class Newsletter {
    private String email;
    private boolean enabled;

    public Newsletter() {}
    // getter setter...

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}