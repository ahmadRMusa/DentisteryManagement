package org.hicham.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ImageProtheseFixe")
public class ImageProtheseFixe {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column (name="id" ,nullable=false)
	int id;
	@Column(name="lien",nullable= false)
	String lien;
	@ManyToOne
    @JoinColumn(name="id_Fix")
    private ProtheseFixe protheseFixe;

	public ImageProtheseFixe(String lien) {
		this.lien = lien;
	}

	public ImageProtheseFixe() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLien() {
		return lien;
	}

	public void setLien(String lien) {
		this.lien = lien;
	}

	public ProtheseFixe getProtheseFixe() {
		return protheseFixe;
	}

	public void setProtheseFixe(ProtheseFixe protheseFixe) {
		this.protheseFixe = protheseFixe;
	}
	
	
}
