package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the slika database table.
 * 
 */
@Entity
@NamedQuery(name="Slika.findAll", query="SELECT s FROM Slika s")
public class Slika implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSlika;

	@Lob
	private byte[] slika;

	//bi-directional many-to-one association to Koncert
	@ManyToOne
	private Koncert koncert;

	public Slika() {
	}

	public int getIdSlika() {
		return this.idSlika;
	}

	public void setIdSlika(int idSlika) {
		this.idSlika = idSlika;
	}

	public byte[] getSlika() {
		return this.slika;
	}

	public void setSlika(byte[] slika) {
		this.slika = slika;
	}

	public Koncert getKoncert() {
		return this.koncert;
	}

	public void setKoncert(Koncert koncert) {
		this.koncert = koncert;
	}

}