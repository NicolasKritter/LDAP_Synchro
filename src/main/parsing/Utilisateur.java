package main.parsing;



public class Utilisateur {

	
    private String login;

    private String nom;
    
//    @Column(nullable=false, length=20)
//    private String role;
   
    private String password;

// 
//    @Column(name="photo_profile", length=200)
//    private String photoProfile;
    
    private String prenom;
    
    private Ecole ecole;
    
    public Utilisateur() {
    	
    }

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Ecole getEcole() {
		return ecole;
	}

	public void setEcole(Ecole ecole) {
		this.ecole = ecole;
	}

	@Override
	public String toString() {
		return "{" + 
				"  \"login\": "+"  \""+login+"\"," + 
				"  \"password\": "+"  \""+password+"\"," + 
				"  \"nom\": "+"  \""+nom+"\"," + 
				"  \"prenom\":"+"  \""+prenom+"\"," + 
				"  \"ecole\":"+ecole+
				"}";
	}
    
    
}
