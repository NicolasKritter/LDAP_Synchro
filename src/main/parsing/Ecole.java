package main.parsing;

public class Ecole {
	private int id;

	public Ecole(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{\"id\":"+id+"}";
	}
}
